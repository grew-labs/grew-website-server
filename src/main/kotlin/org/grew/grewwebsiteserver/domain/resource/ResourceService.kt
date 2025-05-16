package org.grew.grewwebsiteserver.domain.resource

import org.grew.grewwebsiteserver.domain.resource.dto.ResourceUploadUrlDto
import org.grew.grewwebsiteserver.domain.resource.dto.ResourceUploadUrlRequestDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.util.*

@Service
class ResourceService(
    private val s3Presigner: S3Presigner,
    @Value("\${cloud.aws.region.static}")
    private val regionName: String,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucketName: String
) {

    fun generateUploadUrl(request: ResourceUploadUrlRequestDto): ResourceUploadUrlDto {
        // 파일이름이 고유한 키가 되어 저장되기 때문에, unique한 파일명을 만듭니다.
        val randomFileName = "${UUID.randomUUID()}_${request.fileName}"

        return ResourceUploadUrlDto(
            fileName = randomFileName,
            contentType = request.contentType,
            uploadUrl = generatePresignedUrl(fileName = randomFileName),
            downloadUrl = generateDownloadUrl(fileName = randomFileName)
        )
    }

    private fun generatePresignedUrl(fileName: String): String {
        val objectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .putObjectRequest(objectRequest)
            .signatureDuration(java.time.Duration.ofHours(1))
            .build()

        return s3Presigner.presignPutObject(presignRequest).url().toString()
    }

    // S3 버킷 리소스에 public 접근이 가능하기에, download url은 따로 Presigned가 필요없음
    private fun generateDownloadUrl(fileName: String): String {
        return "https://${bucketName}.s3.${regionName}.amazonaws.com/${fileName}"
    }
}
