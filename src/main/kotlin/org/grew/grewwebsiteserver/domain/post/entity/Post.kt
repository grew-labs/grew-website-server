package org.grew.grewwebsiteserver.domain.post.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postId: Long = 0,

    @Column(nullable = true, unique = false)
    val userId: Long?,

    @Column(nullable = false, unique = false)
    var title: String,

    // 저장되는 값은 jsonString
    @Column(nullable = false, unique = false)
    var contents: List<String>,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    fun update(title: String, contents: List<String>) {
        this.title = title
        this.contents = contents
        this.updatedAt = LocalDateTime.now()
    }
}
