package org.grew.grewwebsiteserver.domain.auth

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import java.util.*
import kotlin.test.Test
import java.security.Key
import io.jsonwebtoken.security.Keys



class JwtServiceTest {

    private val secret = "aHR0cHM6Ly9ncmV3Lm9yZy9zZWNyZXQxM3Lm9yZy9zZWNyZXQxM3Lm9yZy9zZWNyZXQxM3Lm9yZy9zZWNyZXQxMjM0NTY="  // 예시 BASE64 인코딩된 키 (길이 충분히 확보)
    private val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    @Test
    fun `test generate long-lived access token manually`() {
        val userId = 1234
        val roles = listOf("ADMIN")

        // 유효기간 365일로 설정
        val validity = Date(System.currentTimeMillis() + 10L * 365 * 24 * 60 * 60 * 1000)

        val token = Jwts.builder()
            .setSubject(userId.toString())
            .claim("authorizations", roles)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        println("✅ Generated Test JWT:\n$token")
    }
}