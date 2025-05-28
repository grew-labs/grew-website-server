package org.grew.grewwebsiteserver.domain.post

import org.grew.grewwebsiteserver.common.Response
import org.grew.grewwebsiteserver.common.ResponseDto
import org.grew.grewwebsiteserver.domain.post.dto.PostCreateRequestDto
import org.grew.grewwebsiteserver.domain.post.dto.PostResponseDto
import org.grew.grewwebsiteserver.domain.post.dto.PostUpdateRequestDto
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): ResponseDto<PostResponseDto> {
        return try {
            val data = postService.getPostByPostId(postId = id)
            Response.success(data)
        } catch (e: IllegalArgumentException) {
            Response.failure(errorMessage = e.message)
        } catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }

    @GetMapping
    fun getPosts(): ResponseDto<List<PostResponseDto>> {
        return try {
            val data = postService.getPosts()
            Response.success(data)
        }  catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun createPost(@RequestBody request: PostCreateRequestDto): ResponseDto<PostResponseDto> {
        return try {
            val data = postService.createPost(request = request)
            Response.success(data)
        } catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updatePost(@PathVariable id: Long, @RequestBody request: PostUpdateRequestDto): ResponseDto<PostResponseDto> {
        return try {
            val data = postService.updatePost(postId = id, request = request)
            Response.success(data)
        } catch (e: IllegalArgumentException) {
            Response.failure(errorMessage = e.message)
        } catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deletePost(@PathVariable id: Long): ResponseDto<Long> {
        return try {
            postService.deletePost(postId = id)
            Response.success(id)
        } catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }
}
