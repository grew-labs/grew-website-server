package org.grew.grewwebsiteserver.domain.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.grew.grewwebsiteserver.common.Log
import org.grew.grewwebsiteserver.domain.auth.entity.RefreshToken
import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.context.request.NativeWebRequest
import java.security.Key
import java.time.LocalDateTime
import java.util.*

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.token_expires_in_sec}") private val tokenExpiresInSec: Long,
    @Value("\${jwt.refresh_token_expires_in_sec}") private val refreshTokenExpiresInSec: Long,
    private val refreshTokenRepository: RefreshTokenRepository,
) : InitializingBean, Log() {

    private lateinit var key: Key
    private var tokenValidityInMillySeconds: Long = 0
    private var refreshTokenValidityInMillySeconds: Long = 0

    override fun afterPropertiesSet() {
        val keyBytes = Decoders.BASE64.decode(secret)
        this.key = Keys.hmacShaKeyFor(keyBytes)
        tokenValidityInMillySeconds = tokenExpiresInSec * 1000
        refreshTokenValidityInMillySeconds = refreshTokenExpiresInSec * 1000
    }

    fun createTokenInfo(user: User): TokenResponse {
        val roles = listOf(user.role).map { it.name }// listOf(user.authType.name) // 또는 user.roles.map { it.name } 등으로 확장
        val userId = requireNotNull(user.userId) { "userId must not be null" }

        val accessToken = createAccessToken(userId, roles)
        val refreshToken = createRefreshToken(userId, roles)

        return TokenResponse(
            tokenType = "Bearer",
            accessToken = accessToken,
            expiresIn = (tokenValidityInMillySeconds / 1000).toInt(),
            refreshToken = refreshToken,
            refreshTokenExpiresIn = (refreshTokenValidityInMillySeconds / 1000).toInt()
        )
    }

    fun refreshTokenInfo(refreshToken: String): TokenResponse {
        val accessToken = reissueAccessToken(refreshToken)
            ?: throw IllegalArgumentException("유효하지 않은 리프레쉬 토큰입니다.")
        val refreshToken = createRefreshToken(getUserId(refreshToken), getAuthorizations(refreshToken))
        return TokenResponse(
            tokenType = "Bearer",
            accessToken = accessToken,
            expiresIn = (tokenValidityInMillySeconds / 1000).toInt(),
            refreshToken = refreshToken,
            refreshTokenExpiresIn = (refreshTokenValidityInMillySeconds / 1000).toInt()
        )
    }


    fun createAccessToken(userId: Long, roles: List<String>): String {
        val validity = Date(System.currentTimeMillis() + tokenValidityInMillySeconds)
        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("authorizations", roles)  // ✅ 권한 리스트 추가
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun getUserId(token: String): Long {
        val body = Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token)
            .body
        return body.subject.toLong()
    }

    fun getAuthorizations(token: String): List<String> {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body

            val rawRoles = claims["authorizations"]
            when (rawRoles) {
                is List<*> -> rawRoles.filterIsInstance<String>() // ["USER", "ADMIN"] 형태
                is String -> listOf(rawRoles)                    // "USER" 단일 문자열일 경우
                else -> emptyList()
            }
        } catch (e: Exception) {
            log.error("권한 파싱 중 오류 발생: ${e.message}")
            emptyList()
        }
    }

    fun createRefreshToken(userId: Long, roles: List<String>): String {
        val validity = Date(System.currentTimeMillis() + refreshTokenValidityInMillySeconds)
        val refreshTokenString = Jwts.builder()
            .setSubject(userId.toString())
            .claim("authorizations", roles)  // ✅ 권한 리스트 추가
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()

        val refreshToken = RefreshToken(
            userId = userId,
            token = refreshTokenString,
            expiration = validity.toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime()
        )
        refreshTokenRepository.save(refreshToken)

        return refreshTokenString
    }

    fun reissueAccessToken(refreshToken: String): String? {
        validateToken(refreshToken)  // JWT 서명 및 만료 확인

        val saved = refreshTokenRepository.findByToken(refreshToken)
            ?: throw IllegalArgumentException("JWT 토큰이 유효하지 않습니다.")

        // DB에 저장된 토큰과 일치하는지 확인
        if (saved.token != refreshToken)
            throw IllegalArgumentException("JWT 토큰이 유효하지 않습니다.")

        // ⏰ DB에 저장된 만료시간이 지났으면 삭제 후 null 반환
        if (saved.expiration.isBefore(LocalDateTime.now())) {
            refreshTokenRepository.deleteById(getUserId(refreshToken))
            throw IllegalArgumentException("리프레시 토큰이 만료되었습니다.")
        }

        return createAccessToken(
            getUserId(refreshToken),
            getAuthorizations(refreshToken)
        )
    }

    fun validateToken(token: String) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        } catch (e: Exception) {
            throw IllegalArgumentException("JWT 토큰이 유효하지 않습니다.")
        }
    }

    fun getToken(request: NativeWebRequest): String {
        return getToken(Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)))
    }

    fun getToken(authorization: Optional<String>): String {
        val splitAuthorization = authorization
            .orElseThrow { NullPointerException("header에 authorization 값이 없습니다.") }
            .split(" ")
        return if (splitAuthorization.size > 1) splitAuthorization[1] else splitAuthorization[0]
    }
}
