package org.grew.grewwebsiteserver.domain.post.dto

import org.grew.grewwebsiteserver.domain.post.entity.PostCategory

data class PostCreateRequestDto(
    val title: String,
    val content: String,
    val category: PostCategory
)
