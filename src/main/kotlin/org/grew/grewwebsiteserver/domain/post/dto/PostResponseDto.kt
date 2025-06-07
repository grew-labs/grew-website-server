package org.grew.grewwebsiteserver.domain.post.dto

import org.grew.grewwebsiteserver.domain.post.entity.Post
import java.time.LocalDateTime

data class PostResponseDto(
    val postId: Long,
    val title: String,
    val content: String,
    val updatedAt: LocalDateTime
) {

    companion object {
        fun from(entity: Post): PostResponseDto {
            return PostResponseDto(
                postId = entity.postId,
                title = entity.title,
                content = entity.content,
                updatedAt = entity.updatedAt
            )
        }
    }
}
