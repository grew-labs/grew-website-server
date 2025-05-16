package org.grew.grewwebsiteserver.domain.resource.dto

data class ResourceUploadUrlDto(
    val fileName: String,
    val contentType: String,
    val uploadUrl: String, // 업로드용 URL
    val downloadUrl: String // 다운로드용 URL
)
