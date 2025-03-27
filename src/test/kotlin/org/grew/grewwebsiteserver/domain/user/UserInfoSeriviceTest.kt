package org.grew.grewwebsiteserver.domain.user

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.grew.grewwebsiteserver.domain.user.entity.User
import java.util.*

class UserInfoServiceTest : StringSpec({
    val userRepository = mockk<UserRepository>()
    val userInfoService = UserInfoSerivice(userRepository)

    "should create a user successfully" {
        val user = User(id = 1L, login_id = "Test User", email = "test@example.com")
        every { userRepository.save(user) } returns user

        val createdUser = userInfoService.createUser(user)

        createdUser shouldBe user
        verify(exactly = 1) { userRepository.save(user) }
    }

    "should return user by id" {
        val user = User(id = 1L, login_id = "Test User", email = "test@example.com")
        every { userRepository.findById(1L) } returns Optional.of(user)

        val foundUser = userInfoService.getUser(1L)

        foundUser shouldBe user
        verify(exactly = 1) { userRepository.findById(1L) }
    }

    "should return null when user not found" {
        every { userRepository.findById(2L) } returns Optional.empty()

        val foundUser = userInfoService.getUser(2L)

        foundUser shouldBe null
        verify(exactly = 1) { userRepository.findById(2L) }
    }
})
