package org.grew.grewwebsiteserver.domain.post.dto

data class PostCreateRequestDto(
    val title: String,
    val contents: List<PostContentDto>
)
