package org.grew.grewwebsiteserver.admin.church

import org.grew.grewwebsiteserver.domain.church.entity.Church

data class PostChurchRequestDto(
    val name: String?,
    val pastor_name: String?,
    val website_url: String?,
    val address: String?,
    val email: String?,
    val phone: String?,
    val description: String?,
    val worship_times: String?,
    val location_lat: Double?,
    val location_lng: Double?
) {
    fun toEntity(): Church {
        return Church(
            name = name ?: "",
            pastorName = pastor_name,
            websiteUrl = website_url,
            address = address,
            email = email,
            phone = phone,
            description = description,
            worshipTimes = worship_times,
            locationLat = location_lat,
            locationLng = location_lng
        )
    }
}

data class PostChurchResponseDto(
    val id: Long,
    val name: String,
    val pastor_name: String?,
    val website_url: String?,
    val address: String?,
    val email: String?,
    val phone: String?,
    val description: String?,
    val worship_times: String?,
    val location_lat: Double?,
    val location_lng: Double?
) {
    companion object {
        fun from(entity: Church): PostChurchResponseDto {
            return PostChurchResponseDto(
                id = entity.id,
                name = entity.name,
                pastor_name = entity.pastorName,
                website_url = entity.websiteUrl,
                address = entity.address,
                email = entity.email,
                phone = entity.phone,
                description = entity.description,
                worship_times = entity.worshipTimes,
                location_lat = entity.locationLat,
                location_lng = entity.locationLng
            )
        }
    }
}
