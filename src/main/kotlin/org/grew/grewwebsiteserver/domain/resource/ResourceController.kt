package org.grew.grewwebsiteserver.domain.resource

import org.grew.grewwebsiteserver.common.Response
import org.grew.grewwebsiteserver.common.ResponseDto
import org.grew.grewwebsiteserver.domain.resource.dto.ResourceUploadUrlDto
import org.grew.grewwebsiteserver.domain.resource.dto.ResourceUploadUrlRequestDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/resource")
class ResourceController(
    private val resourceService: ResourceService
) {


    @PostMapping("/uploadUrl")
    fun generateUploadUrl(@RequestBody request: ResourceUploadUrlRequestDto): ResponseDto<ResourceUploadUrlDto> {
        return try {
            val data = resourceService.generateUploadUrl(request = request)
            Response.success(data)
        } catch (e: Exception) {
            Response.unexpectedException(e)
        }
    }
}
