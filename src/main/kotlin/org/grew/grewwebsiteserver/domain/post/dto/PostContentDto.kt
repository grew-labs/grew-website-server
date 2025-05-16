package org.grew.grewwebsiteserver.domain.post.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = PostContentDto.Text::class, name = "text"),
    JsonSubTypes.Type(value = PostContentDto.Image::class, name = "image")
)
@Serializable
sealed class PostContentDto {

    @Serializable
    @SerialName("text")
    data class Text(
        val text: String
    ): PostContentDto()

    @Serializable
    @SerialName("image")
    data class Image(
        val imageUrl: String,
        val contentType: String
    ): PostContentDto()

    fun toJsonString(): String {
        return json.encodeToString(this)
    }

    companion object {

        val json = Json { classDiscriminator = "type" }

        fun from(jsonString: String): PostContentDto {
            return json.decodeFromString<PostContentDto>(jsonString)
        }
    }
}
