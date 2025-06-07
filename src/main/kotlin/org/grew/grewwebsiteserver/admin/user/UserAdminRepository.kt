package org.grew.grewwebsiteserver.admin.user

import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.data.repository.CrudRepository

interface UserAdminRepository : CrudRepository<User, Long>