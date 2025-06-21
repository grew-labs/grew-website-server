package org.grew.grewwebsiteserver.domain.post.dto

import org.grew.grewwebsiteserver.domain.post.entity.PostCategory

data class PostListRequestDto(
    val category: PostCategory
)
