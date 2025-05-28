package org.grew.grewwebsiteserver.domain.post

import org.grew.grewwebsiteserver.domain.post.dto.PostCreateRequestDto
import org.grew.grewwebsiteserver.domain.post.dto.PostResponseDto
import org.grew.grewwebsiteserver.domain.post.dto.PostUpdateRequestDto
import org.grew.grewwebsiteserver.domain.post.entity.Post
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun getPosts(): List<PostResponseDto> {
        return postRepository.findAll().toList().map { PostResponseDto.from(entity = it) }
    }

    fun getPostByPostId(postId: Long): PostResponseDto {
        val entity = postRepository.findById(postId).getOrNull()
            ?: throw IllegalArgumentException("id에 해당하는 게시물이 없습니다.")
        return PostResponseDto.from(entity = entity)
    }

    fun createPost(request: PostCreateRequestDto): PostResponseDto {
        val entity = Post(
            userId = null,
            title = request.title,
            content = request.content
        )
        postRepository.save(entity)
        return PostResponseDto.from(entity = entity)
    }

    fun updatePost(postId: Long, request: PostUpdateRequestDto): PostResponseDto {
        val entity = postRepository.findById(postId).getOrNull()
            ?: throw IllegalArgumentException("id에 해당하는 게시물이 없습니다.")
        entity.update(title = request.title, content = request.content)
        return PostResponseDto.from(entity = entity)
    }

    fun deletePost(postId: Long) {
        postRepository.deleteById(postId)
    }
}
