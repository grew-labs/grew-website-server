package org.grew.grewwebsiteserver.domain.hello.dto

import io.swagger.v3.oas.annotations.media.Schema

class PostHelloDto(
    @field:Schema(description = "Name of the person", example = "Haru Oh")
    var name: String
)