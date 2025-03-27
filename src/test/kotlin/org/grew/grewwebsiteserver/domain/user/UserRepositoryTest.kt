package org.grew.grewwebsiteserver.domain.user

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import jakarta.transaction.Transactional
import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = ["classpath:application-test.yml"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class UserRepositoryTest(
    private val userRepository: UserRepository
) : BehaviorSpec({

    // SpringTestLifecycleMode: 테스트 내 트랜잭션 등을 묶는 단위 설정
    // ROOT: 테스트 메소드 전체를 하나의 트랜잭션으로 묶음, TEST: 각 테스트 메소드(then절)를 하나의 트랜잭션으로 묶음
    // https://effortguy.tistory.com/475
    extensions(SpringTestExtension(SpringTestLifecycleMode.Test))

    given("UserRepository가 주어졌을 때") {
        `when`("유저를 저장하면") {
            then("정상적으로 저장된다") {
                val user = User(id = null, login_id = "testUser", email = "test@example.com")
                val savedUser = userRepository.save(user)
                savedUser.id shouldBe 1L
                savedUser.login_id shouldBe "testUser"
                savedUser.email shouldBe "test@example.com"
            }
        }

        `when`("저장된 유저를 단일 조회하면") {
            then("정상적으로 조회된다") {
                val user = User(id = null, login_id = "testUser", email = "test@example.com")
                val savedUser = userRepository.save(user)
                val foundUser = userRepository.findById(savedUser.id!!).orElse(null)
                foundUser shouldBe savedUser
            }
        }

        `when`("여러 유저를 저장한 후 리스트 조회하면") {
            then("저장된 모든 유저가 조회된다") {
                val users = listOf(
                    User(id = null, login_id = "user1", email = "user1@example.com"),
                    User(id = null, login_id = "user2", email = "user2@example.com")
                )
                userRepository.saveAll(users)
                val foundUsers = userRepository.findAll().toList()
                foundUsers.size shouldBe 2
            }
        }

        `when`("저장된 유저의 정보를 수정하면") {
            then("변경 사항이 정상적으로 반영된다") {
                val user = User(id = null, login_id = "testUser", email = "test@example.com")
                val savedUser = userRepository.save(user)
                val updatedUser = savedUser.copy(email = "new@example.com")
                val result = userRepository.save(updatedUser)
                result.email shouldBe "new@example.com"
            }
        }

        `when`("저장된 유저를 삭제하면") {
            then("정상적으로 삭제된다") {
                val user = User(id = null, login_id = "testUser", email = "test@example.com")
                val savedUser = userRepository.save(user)
                userRepository.deleteById(savedUser.id!!)
                userRepository.findById(savedUser.id!!).isPresent shouldBe false
            }
        }

    }
})
