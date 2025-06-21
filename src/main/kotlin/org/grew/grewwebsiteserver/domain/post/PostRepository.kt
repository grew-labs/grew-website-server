package org.grew.grewwebsiteserver.domain.post

import org.grew.grewwebsiteserver.domain.post.entity.Post
import org.grew.grewwebsiteserver.domain.post.entity.PostCategory
import org.springframework.data.repository.CrudRepository

interface PostRepository: CrudRepository<Post, Long> {

    fun findAllByCategory(category: PostCategory): List<Post>
}
