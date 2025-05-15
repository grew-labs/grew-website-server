package org.grew.grewwebsiteserver.domain.post

import org.grew.grewwebsiteserver.domain.auth.kakao.ApiResponse
import org.grew.grewwebsiteserver.domain.post.dto.PostCreateRequestDto
import org.grew.grewwebsiteserver.domain.post.dto.PostResponseDto
import org.grew.grewwebsiteserver.domain.post.dto.PostUpdateRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): ResponseEntity<ApiResponse<PostResponseDto>> {
        val data = postService.getPostByPostId(postId = id) ?: return ResponseEntity.ok(ApiResponse.failure("id에 해당하는 게시물이 없습니다."))
        return ResponseEntity.ok(ApiResponse.success(data))
    }

    @GetMapping
    fun getPosts(): ResponseEntity<ApiResponse<List<PostResponseDto>>> {
        val data = postService.getPosts()
        return ResponseEntity.ok(ApiResponse.success(data))
    }

    @PostMapping
    fun createPost(@RequestBody request: PostCreateRequestDto): ResponseEntity<ApiResponse<PostResponseDto>> {
        return try {
            val data = postService.createPost(request = request)
            ResponseEntity.ok(ApiResponse.success(data))
        } catch (e: Exception) {
            ResponseEntity.ok(ApiResponse.failure("게시물 생성 중 오류가 발생했습니다. ${e.message}"))
        }
    }

    @PatchMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody request: PostUpdateRequestDto): ResponseEntity<ApiResponse<PostResponseDto>> {
        return try {
            val data = postService.updatePost(postId = id, request = request)
            ResponseEntity.ok(ApiResponse.success(data))
        } catch (e: Exception) {
            ResponseEntity.ok(ApiResponse.failure("게시물 업데이트 중 오류가 발생했습니다. ${e.message}"))
        }
    }
}
