package org.grew.grewwebsiteserver.domain.post.entity

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Converter(autoApply = false)
class PostContentsConverter : AttributeConverter<List<PostContent>, String> {

    private val json = Json { classDiscriminator = "type" }

    override fun convertToDatabaseColumn(attribute: List<PostContent>?): String {
        return attribute?.let {
            json.encodeToString(ListSerializer(PostContent.serializer()), it)
        } ?: "[]"
    }

    override fun convertToEntityAttribute(dbData: String?): List<PostContent> {
        return dbData?.takeIf { it.isNotBlank() }?.let {
            json.decodeFromString(ListSerializer(PostContent.serializer()), it)
        } ?: emptyList()
    }
}

