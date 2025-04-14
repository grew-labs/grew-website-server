package org.grew.grewwebsiteserver.domain.auth.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_tokens")
class RefreshToken(
    @Id
    val userId: Long,

    @Column(nullable = false, unique = true)
    var token: String,

    @Column(name = "expiration", nullable = false)
    var expiration: LocalDateTime
)
