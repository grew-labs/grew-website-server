package org.grew.grewwebsiteserver.admin.user

import org.grew.grewwebsiteserver.domain.user.entity.User
import org.grew.grewwebsiteserver.domain.auth.enums.UserRole

data class UserPatchRequest(
    val name: String? = null,
    val email: String? = null,
    val role: String? = null // "USER", "ADMIN"
)

data class UserResponse(
    val id: Long?,
    val name: String,
    val email: String?,
    val role: UserRole,
    val createdAt: String,
    val updatedAt: String
) {
    companion object {
        fun fromEntity(user: User) = UserResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            role = user.role,
            createdAt = user.createdAt.toString(),
            updatedAt = user.updatedAt.toString()
        )
    }
}
