package org.grew.grewwebsiteserver.domain.hello

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.grew.grewwebsiteserver.domain.hello.dto.PostHelloDto
import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springdoc.core.service.SecurityService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
    private val helloService: HelloService,
    private val securityParser: SecurityService
) {

    @Operation(summary = "Hello GET API", description = "GET 요청을 통해 간단한 인사를 반환합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Success", content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = String::class))
                ]
            )
        ]
    )
    @GetMapping("/api/hello")
    fun hello(): String {
        return helloService.hello()
    }

    @Operation(summary = "Hello POST API", description = "POST 요청을 통해 사용자의 이름을 받아 hello를 반환합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Success", content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = String::class))
                ]
            ),
            ApiResponse(responseCode = "400", description = "Invalid request body", content = [Content()])
        ]
    )
    @PostMapping("/api/hello")
    fun helloPost(@RequestBody postHelloDto: PostHelloDto): String {
        return helloService.hello(postHelloDto.name)
    }

    @Operation(
        summary = "Hello Private API",
        description = "인증된 사용자만 접근할 수 있는 API입니다. JWT 토큰을 통해 인증된 사용자 정보를 반환합니다.",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/api/hello/private")
    fun helloPrivate(@AuthenticationPrincipal user: User): ResponseEntity<String> {
        return ResponseEntity.ok("success! user name: ${user.name}, roles: ${user.role}")
    }

    @Operation(
        summary = "Hello Admin API",
        description = "ADMIN 권한을 가진 사용자만 접근할 수 있는 API입니다. JWT 토큰을 통해 인증된 사용자 정보를 반환합니다.",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/hello")
    fun helloAdmin(@AuthenticationPrincipal user: User): ResponseEntity<String> {
        return ResponseEntity.ok("success! user name: ${user.name}, roles: ${user.role}")
    }
}