package org.grew.grewwebsiteserver.domain.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.http.ResponseEntity
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
        summary = "Get User Info",
        description = "Retrieve the authenticated user's information.",
        security = [SecurityRequirement(name = "Authorization")]
    )
    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun getUserInfo(@AuthenticationPrincipal user: User): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(
            UserResponseDto.from(user)
        )
    }

}