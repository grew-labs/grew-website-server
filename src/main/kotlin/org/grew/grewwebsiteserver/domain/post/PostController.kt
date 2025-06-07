package org.grew.grewwebsiteserver.domain.post

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
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
    @Operation(
        summary = "게시글 단건 조회",
        description = "게시글 ID를 이용하여 단일 게시글 정보를 조회합니다."
    )
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
    @Operation(
        summary = "게시글 목록 조회",
        description = "전체 게시글 목록을 조회합니다."
    )
    fun getPosts(): ResponseDto<List<PostResponseDto>> {
        return try {
            val data = postService.getPosts()
            Response.success(data)
        }  catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }

    @PostMapping
    @Operation(
        summary = "게시글 생성",
        description = "관리자가 새로운 게시글을 생성합니다.",
        security = [SecurityRequirement(name = "Authorization")]
    )
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
    @Operation(
        summary = "게시글 수정",
        description = "관리자가 특정 ID의 게시글을 수정합니다.",
        security = [SecurityRequirement(name = "Authorization")]
    )
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
    @Operation(
        summary = "게시글 삭제",
        description = "관리자가 특정 ID의 게시글을 삭제합니다.",
        security = [SecurityRequirement(name = "Authorization")]
    )
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
