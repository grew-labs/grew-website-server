package org.grew.grewwebsiteserver.domain.auth.kakao

import org.grew.grewwebsiteserver.domain.auth.AuthResponseDto
import org.grew.grewwebsiteserver.domain.auth.JwtService
import org.grew.grewwebsiteserver.domain.auth.enums.AuthType
import org.grew.grewwebsiteserver.domain.user.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class KakaoAuthService(
    private val restTemplate: RestTemplate,
    @Value("\${kakao.client-id}") private val clientId: String,
    @Value("\${kakao.redirect-uri}") private val redirectUri: String,
    @Value("\${kakao.token-uri}") private val tokenUri: String,
    @Value("\${kakao.user-info-uri}") private val userInfoUri: String,
    private val userService: UserService,
    private val jwtService: JwtService
) {

    fun kakaoLogin(code: String): AuthResponseDto {
        println("redirectUri: $redirectUri")
        // 1. 카카오 인가코드로 <카카오 엑세스 토큰> 받기
        val kakaoTokenResponse = getTokenByAuthCode(code)
        // 2. 엑세스 토큰으로 카카오에서 유저 정보 조회
        val userInfo = getUserInfo(kakaoTokenResponse.accessToken)
        // 3. 회원가입 또는 로그인 처리
        val user = userService.saveOrUpdate(UserInfoDto(userInfo.id, userInfo.nickname), AuthType.KAKAO)
        // 4. 엑세스 토큰 발급
        val tokenResponse = jwtService.createTokenInfo(user)

        return AuthResponseDto(tokenInfo = tokenResponse, nickname = userInfo.nickname)
    }

    fun getTokenByAuthCode(code: String): KakaoTokenResponse {
        val params = mapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientId,
            "code" to code
        )

        return requestToken(params)
    }

    // kakao token refresh (필요시 사용)
//    fun refreshAccessToken(userId: Long): KakaoTokenResponse {
//        val refreshToken = refreshTokenStore[userId]
//            ?: throw RuntimeException("Refresh token 없음")
//
//        val params = mapOf(
//            "grant_type" to "refresh_token",
//            "client_id" to clientId,
//            "refresh_token" to refreshToken
//        )
//
//        val tokenResponse = requestToken(params)
//
//        // 새 refresh token이 있을 경우 갱신
//        tokenResponse.refreshToken?.let {
//            refreshTokenStore[userId] = it
//        }
//
//        return tokenResponse
//    }

    private fun requestToken(params: Map<String, String>): KakaoTokenResponse {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val body = params.entries.joinToString("&") { "${it.key}=${it.value}" }

        val request = HttpEntity(body, headers)
        val response = restTemplate.postForEntity(tokenUri, request, KakaoTokenResponse::class.java)

        return response.body ?: throw RuntimeException("토큰 응답 오류")
    }

    fun getUserInfo(token: String): KakaoUserInfo {
        val headers = HttpHeaders().apply {
            setBearerAuth(token)
        }

        val entity = HttpEntity(null, headers)
        val response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, KakaoUserInfo::class.java)
        return response.body ?: throw RuntimeException("사용자 정보 없음")
    }
}
