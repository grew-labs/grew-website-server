package org.grew.grewwebsiteserver.domain.user

import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class UserInfoSerivice(
    private val userRepository: UserRepository
) {
    fun createUser(user: User): User? {
        return userRepository.save(user)
    }

    fun getUser(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

}