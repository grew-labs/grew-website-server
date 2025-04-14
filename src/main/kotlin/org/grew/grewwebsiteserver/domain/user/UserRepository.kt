package org.grew.grewwebsiteserver.domain.user

import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByUserId(userId: Long): User?
}