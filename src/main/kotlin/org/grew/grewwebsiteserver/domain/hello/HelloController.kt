package org.grew.grewwebsiteserver.domain.hello

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.grew.grewwebsiteserver.domain.hello.dto.PostHelloDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
    private val helloService: HelloService
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
    @GetMapping("/hello")
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
    @PostMapping("/hello")
    fun helloPost(@RequestBody postHelloDto: PostHelloDto): String {
        return helloService.hello(postHelloDto.name)
    }
}