package org.grew.grewwebsiteserver.domain.user

import org.grew.grewwebsiteserver.domain.user.entity.User

data class UserResponseDto(
    val nickname: String?,
    val email: String?,
    val joinedDate: String?,
    val role: String?,
) {
    companion object {
        fun from(user: User): UserResponseDto {
            return UserResponseDto(
                nickname = user.name,
                email = user.email,
                joinedDate = user.createdAt.toString(),
                role = user.role.name
            )
        }
    }
}