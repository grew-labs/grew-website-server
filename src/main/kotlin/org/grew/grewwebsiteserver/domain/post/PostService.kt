package org.grew.grewwebsiteserver.domain.post

import org.grew.grewwebsiteserver.domain.post.dto.PostCreateRequestDto
import org.grew.grewwebsiteserver.domain.post.dto.PostResponseDto
import org.grew.grewwebsiteserver.domain.post.dto.PostUpdateRequestDto
import org.grew.grewwebsiteserver.domain.post.entity.Post
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun getPosts(): List<PostResponseDto> {
        return postRepository.findAll().toList().map { PostResponseDto.from(entity = it) }
    }

    fun getPostByPostId(postId: Long): PostResponseDto? {
        val entity = postRepository.findByPostId(postId) ?: return null
        return PostResponseDto.from(entity = entity)
    }

    fun createPost(request: PostCreateRequestDto): PostResponseDto {
        val entity = Post(
            userId = null,
            title = request.title,
            contents = request.contents.map { it.toJsonString() }
        )
        postRepository.save(entity)
        return PostResponseDto.from(entity = entity)
    }

    fun updatePost(postId: Long, request: PostUpdateRequestDto): PostResponseDto {
        val entity = postRepository.findByPostId(postId) ?: throw IllegalArgumentException("id에 해당하는 게시물이 없습니다.")
        entity.update(title = request.title, contents = request.contents.map { it.toJsonString() })
        return PostResponseDto.from(entity = entity)
    }

    fun deletePost(postId: Long) {
        postRepository.deleteById(postId)
    }
}
