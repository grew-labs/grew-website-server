package org.grew.grewwebsiteserver.domain.auth.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTokenResponse(
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("expires_in") val expiresIn: Int,
    @JsonProperty("refresh_token") val refreshToken: String?,
    @JsonProperty("refresh_token_expires_in") val refreshTokenExpiresIn: Int?
)


data class KakaoUserInfo(
    @JsonProperty("id") val id: Long,
    @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount,
    @JsonProperty("connected_at") val connectedAt: String
) {
    val nickname: String get() = kakaoAccount.profile.nickname
}

data class KakaoAccount(
    val profile: Profile
)

data class Profile(
    val nickname: String
)
