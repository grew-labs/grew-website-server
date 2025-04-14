package org.grew.grewwebsiteserver.domain.auth.kakao

// todo: APIResponse 공통으로 분리하기
data class ApiResponse<T>(
    val message: String,
    val data: T?
)

data class UserInfoDto(
    val userId: Long,
    val nickname: String
)