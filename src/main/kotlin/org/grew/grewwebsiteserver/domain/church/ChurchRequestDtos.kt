package org.grew.grewwebsiteserver.domain.church

import org.grew.grewwebsiteserver.domain.church.entity.Church


data class ChurchDetailResponse(
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
        fun from(entity: Church): ChurchDetailResponse {
            return ChurchDetailResponse(
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

data class ChurchListItem(
    val id: Long,
    val name: String,
    val pastor_name: String?,
    val address: String?,
    val location_lat: Double?,
    val location_lng: Double?
) {
    companion object {
        fun from(entity: Church): ChurchListItem {
            return ChurchListItem(
                id = entity.id,
                name = entity.name,
                pastor_name = entity.pastorName,
                address = entity.address,
                location_lat = entity.locationLat,
                location_lng = entity.locationLng
            )
        }
    }
}