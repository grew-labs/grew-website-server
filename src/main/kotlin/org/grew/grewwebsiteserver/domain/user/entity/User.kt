package org.grew.grewwebsiteserver.domain.user.entity

import jakarta.persistence.*
import org.grew.grewwebsiteserver.common.EntityStatus
import org.grew.grewwebsiteserver.domain.auth.enums.AuthType
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type", nullable = false, length = 20)
    val authType: AuthType, // 인증 타입 (KAKAO, GOOGLE, APPLE 등)

    @Column(unique = true)
    val userId: Long? = null,  // 카카오 고유 ID

    @Column(unique = true, length = 100)
    val email: String? = null,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: EntityStatus = EntityStatus.ACTIVE
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}

