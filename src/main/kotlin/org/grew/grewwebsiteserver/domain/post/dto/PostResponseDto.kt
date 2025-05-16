package org.grew.grewwebsiteserver.domain.post.dto

import org.grew.grewwebsiteserver.domain.post.entity.Post
import java.time.LocalDateTime

data class PostResponseDto(
    val postId: Long,
    val title: String,
    val contents: List<PostContentDto>,
    val updatedAt: LocalDateTime
) {

    companion object {
        fun from(entity: Post): PostResponseDto {
            return PostResponseDto(
                postId = entity.postId,
                title = entity.title,
                contents = entity.contents.map { PostContentDto.from(jsonString = it) },
                updatedAt = entity.updatedAt
            )
        }
    }
}
