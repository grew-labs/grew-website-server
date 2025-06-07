package org.grew.grewwebsiteserver.domain.post.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postId: Long = 0,

    @Column(nullable = true, unique = false)
    val userId: Long?,

    @Column(nullable = false, unique = false, columnDefinition = "TEXT")
    var title: String,

    @Column(nullable = false, unique = false, columnDefinition = "TEXT")
    var content: String,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    fun update(title: String, content: String) {
        this.title = title
        this.content = content
        this.updatedAt = LocalDateTime.now()
    }
}
