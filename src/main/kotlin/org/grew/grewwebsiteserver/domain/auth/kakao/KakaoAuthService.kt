package org.grew.grewwebsiteserver.domain.auth.kakao

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
    @Value("\${kakao.user-info-uri}") private val userInfoUri: String
) {
    // 임시 저장 (실제로는 DB나 Redis에 저장해야 함)
    private var refreshTokenStore: MutableMap<Long, String> = mutableMapOf()

    fun kakaoLogin(code: String): AuthResponseDto {
        val tokenResponse = getTokenByAuthCode(code)
        val userInfo = getUserInfo(tokenResponse.accessToken)

        // 새 refresh token이 있을 경우 갱신
        tokenResponse.refreshToken?.let {
            refreshTokenStore[userInfo.id] = it
        }

        return AuthResponseDto(tokenInfo = tokenResponse, nickname = userInfo.nickname)
    }

    fun getTokenByAuthCode(code: String): KakaoTokenResponse {
        val params = mapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientId,
            "redirect_uri" to redirectUri,
            "code" to code
        )

        return requestToken(params)
    }

    fun refreshAccessToken(userId: Long): KakaoTokenResponse {
        val refreshToken = refreshTokenStore[userId]
            ?: throw RuntimeException("Refresh token 없음")

        val params = mapOf(
            "grant_type" to "refresh_token",
            "client_id" to clientId,
            "refresh_token" to refreshToken
        )

        val tokenResponse = requestToken(params)

        // 새 refresh token이 있을 경우 갱신
        tokenResponse.refreshToken?.let {
            refreshTokenStore[userId] = it
        }

        return tokenResponse
    }

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
        println("response: ${response.body}")
        println("response: ${response.body?.id}")
        return response.body ?: throw RuntimeException("사용자 정보 없음")
    }
}
