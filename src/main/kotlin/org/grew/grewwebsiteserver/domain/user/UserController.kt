package org.grew.grewwebsiteserver.domain.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.grew.grewwebsiteserver.common.Response
import org.grew.grewwebsiteserver.common.ResponseDto
import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {

    @GetMapping("/me")
    @Operation(
        summary = "내 계정 정보 조회",
        description = "인증된 사용자의 상세 정보를 반환합니다.",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun getUserInfo(@AuthenticationPrincipal user: User): ResponseDto<UserResponseDto> {
        return try {
            return Response.success(
                UserResponseDto.from(user)
            )
        } catch (e: IllegalArgumentException) {
            Response.failure(errorMessage = e.message)
        } catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }


    @GetMapping("/me/role")
    @Operation(
        summary = "내 권한 조회",
        description = "인증된 사용자의 권한 정보를 반환합니다.",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun getUserRole(@AuthenticationPrincipal user: User): ResponseDto<UserRoleResponseDto> {
        return try {
            return Response.success(
                UserRoleResponseDto.from(user)
            )
        } catch (e: IllegalArgumentException) {
            Response.failure(errorMessage = e.message)
        } catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }

}