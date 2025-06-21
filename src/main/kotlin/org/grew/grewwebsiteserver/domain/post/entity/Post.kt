package org.grew.grewwebsiteserver.domain.post.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postId: Long = 0,

    @Column(nullable = false, unique = false, columnDefinition = "TEXT")
    var title: String,

    @Column(nullable = false, unique = false, columnDefinition = "TEXT")
    var content: String,

    @Column(nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    var category: PostCategory,

    @Column(nullable = false, unique = false)
    var authorUserId: Long,

    @Column(nullable = false, unique = false)
    var authorName: String,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {

    fun update(title: String, content: String, authorUserId: Long, authorName: String) {
        this.title = title
        this.content = content
        this.authorUserId = authorUserId
        this.authorName = authorName
        this.updatedAt = LocalDateTime.now()
    }
}
