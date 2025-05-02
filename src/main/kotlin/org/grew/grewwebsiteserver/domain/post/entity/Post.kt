package org.grew.grewwebsiteserver.domain.post.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue
    val postId: Long,

    @Column(nullable = false, unique = false)
    val userId: Long,

    @Column(nullable = false, unique = false)
    var title: String,

    @Convert(converter = PostContentsConverter::class)
    @Column(columnDefinition = "TEXT")
    var contents: List<PostContent>,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
