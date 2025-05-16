package org.grew.grewwebsiteserver.domain.post.dto

data class PostUpdateRequestDto(
    val title: String,
    val contents: List<PostContentDto>
)

