package org.grew.grewwebsiteserver.domain.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponseDto(
    val tokenInfo: TokenResponse,
    val nickname: String?
)

data class TokenResponse(
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("expires_in") val expiresIn: Int,
    @JsonProperty("refresh_token") val refreshToken: String?,
    @JsonProperty("refresh_token_expires_in") val refreshTokenExpiresIn: Int?
)