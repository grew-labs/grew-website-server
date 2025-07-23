package org.grew.grewwebsiteserver.domain.post.dto

import org.grew.grewwebsiteserver.domain.post.entity.Post
import org.grew.grewwebsiteserver.domain.post.entity.PostCategory
import java.time.LocalDateTime

data class PostResponseDto(
    val postId: Long,
    val title: String,
    val content: String,
    val authorName: String,
    val category: PostCategory,
    val updatedAt: LocalDateTime
) {

    companion object {
        fun from(entity: Post): PostResponseDto = PostResponseDto(
            postId = entity.postId,
            title = entity.title,
            content = entity.content,
            authorName = entity.authorName,
            category = entity.category,
            updatedAt = entity.updatedAt
        )
    }
}
