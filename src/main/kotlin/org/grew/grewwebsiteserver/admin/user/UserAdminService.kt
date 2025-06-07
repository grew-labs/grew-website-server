package org.grew.grewwebsiteserver.admin.user

import org.grew.grewwebsiteserver.domain.auth.enums.UserRole
import org.springframework.stereotype.Service

@Service
class UserAdminService(
    private val userAdminRepository: UserAdminRepository
) {

    fun getAllUsers(): List<UserResponse> {
        return userAdminRepository.findAll().map { UserResponse.fromEntity(it) }
    }

    fun updateUser(id: Long, request: UserPatchRequest): UserResponse {
        val user = userAdminRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User with ID $id not found") }

        request.name?.let { user.name = it }
        request.email?.let { user.email = it }
        request.role?.let {
            user.role = UserRole.from(it)  // enum에서 검증 수행
        }

        return UserResponse.fromEntity(userAdminRepository.save(user))
    }
}