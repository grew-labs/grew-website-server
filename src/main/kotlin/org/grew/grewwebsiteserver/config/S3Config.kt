package org.grew.grewwebsiteserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class S3Config(
    @Value("\${cloud.aws.credentials.access-key}")
    private val accessKey: String,
    @Value("\${cloud.aws.credentials.secret-key}")
    private val secretKey: String,
    @Value("\${cloud.aws.region.static}")
    private val regionName: String
) {

    @Bean
    fun s3Client(): S3Client {
        val credentials = AwsBasicCredentials.create(accessKey, secretKey)
        return S3Client.builder()
            .region(Region.of(regionName))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }

    @Bean
    fun s3Presigner(): S3Presigner {
        val credentials = AwsBasicCredentials.create(accessKey, secretKey)
        return S3Presigner.builder()
            .region(Region.of(regionName))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }
}
