package org.grew.grewwebsiteserver.domain.resource.dto

data class ResourceUploadUrlRequestDto(
    val fileName: String,
    val contentType: String
)
