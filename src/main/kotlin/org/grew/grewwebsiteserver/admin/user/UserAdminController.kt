package org.grew.grewwebsiteserver.admin.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.grew.grewwebsiteserver.common.Response
import org.grew.grewwebsiteserver.common.ResponseDto
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
@RequestMapping("/admin/users")
class UserAdminController(
    private val userAdminService: UserAdminService
) {

    @GetMapping
    @Operation(
        summary = "사용자 목록 조회",
        description = "전체 사용자 목록을 조회합니다.",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PreAuthorize("hasRole('ADMIN')")
    fun getUsers(): ResponseDto<List<UserResponse>> {
        return try {
            val users = userAdminService.getAllUsers()
            return Response.success(users)
        } catch (e: IllegalArgumentException) {
            Response.failure(errorMessage = e.message)
        } catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }

    @PatchMapping("/{id}")
    @Operation(
        summary = "사용자 정보 수정",
        description = "지정한 사용자의 이름, 이메일, 역할을 수정합니다. 역할은 'USER' 또는 'ADMIN'만 허용됩니다.",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PreAuthorize("hasRole('ADMIN')")
    fun updateUser(
        @PathVariable id: Long,
        @SwaggerRequestBody(
            description = "수정할 사용자 정보",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    examples = [
                        ExampleObject(
                            name = "UserUpdateExample",
                            summary = "사용자 정보 수정 예시",
                            value = """
                            {
                              "name": "홍길동",
                              "email": "hong@example.com",
                              "role": "ADMIN"
                            }
                            """
                        )
                    ]
                )
            ]
        )
        @RequestBody request: UserPatchRequest
    ): ResponseDto<UserResponse> {
        return try {
            val updatedUser = userAdminService.updateUser(id, request)
            return Response.success(updatedUser)
        } catch (e: IllegalArgumentException) {
            Response.failure(errorMessage = e.message)
        } catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }
}