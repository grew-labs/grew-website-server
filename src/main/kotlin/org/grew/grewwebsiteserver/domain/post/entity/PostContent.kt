package org.grew.grewwebsiteserver.domain.post.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// 지원하는 요소가 늘어날 때마다 확장하면 될 듯?
@Serializable
sealed class PostContent {

    @Serializable
    @SerialName("text")
    data class text(
        val text: String
    ): PostContent()

    @Serializable
    @SerialName("image")
    data class image(
        val imageUrl: String,
        val mediaType: String
    ): PostContent()

    companion object {
        fun from(jsonString: String): PostContent? {
            val json = Json { classDiscriminator = "type" }
            return try {
                json.decodeFromString<PostContent>(jsonString)
            } catch (e: SerializationException) {
                null
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
