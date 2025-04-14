package org.grew.grewwebsiteserver.domain.auth.kakao

// todo: APIResponse 공통으로 분리하기
data class ApiResponse<T>(
    val message: String,
    val data: T?
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> = ApiResponse("success", data)
        fun <T> failure(message: String): ApiResponse<T> = ApiResponse(message, null)
    }
}


data class UserInfoDto(
    val userId: Long,
    val nickname: String
)