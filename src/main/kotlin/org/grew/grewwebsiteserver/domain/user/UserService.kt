package org.grew.grewwebsiteserver.domain.user

import org.grew.grewwebsiteserver.domain.auth.enums.AuthType
import org.grew.grewwebsiteserver.domain.auth.kakao.UserInfoDto
import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun saveOrUpdate(userInfo: UserInfoDto, authType: AuthType): User {

        val user = userRepository.findByUserId(userInfo.userId)
            ?.apply {
                this.name = userInfo.nickname
                // this.email = userInfo.email // todo: 카카오 비즈인증 후 email 추가
            }
            ?: User(
                userId = userInfo.userId,
                name = userInfo.nickname,
                // email = userInfo.email, // todo: 카카오 비즈인증 후 email 추가
                authType = authType,
            )

        return userRepository.save(user)
    }

    fun getUserByUserId(userId: Long): User? {
        return userRepository.findByUserId(userId)
    }

}