package org.grew.grewwebsiteserver.domain.post

import org.grew.grewwebsiteserver.domain.post.dto.*
import org.grew.grewwebsiteserver.domain.post.entity.Post
import org.grew.grewwebsiteserver.domain.post.entity.PostCategory
import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun getPosts(categoryString: String): List<PostResponseDto> {
        val category = enumValueOf<PostCategory>(categoryString)
        return postRepository.findAllByCategory(category = category).map { PostResponseDto.from(it) }
    }

    fun getPostByPostId(postId: Long): PostResponseDto {
        val entity = postRepository.findById(postId).getOrNull()
            ?: throw IllegalArgumentException("id에 해당하는 게시물이 없습니다.")
        return PostResponseDto.from(entity = entity)
    }

    fun createPost(request: PostCreateRequestDto, creator: User): PostResponseDto {
        val entity = Post(
            title = request.title,
            category = request.category,
            content = request.content,
            authorUserId = creator.userId ?: -1,
            authorName = creator.name
        )
        postRepository.save(entity)
        return PostResponseDto.from(entity = entity)
    }

    fun updatePost(postId: Long, request: PostUpdateRequestDto, editor: User): PostResponseDto {
        val entity = postRepository.findById(postId).getOrNull()
            ?: throw IllegalArgumentException("id에 해당하는 게시물이 없습니다.")
        entity.update(
            title = request.title,
            content = request.content,
            authorUserId = editor.userId ?: -1,
            authorName = editor.name
        )
        return PostResponseDto.from(entity = entity)
    }

    fun deletePost(postId: Long) {
        postRepository.deleteById(postId)
    }
}
