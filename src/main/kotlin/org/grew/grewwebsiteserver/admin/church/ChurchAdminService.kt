package org.grew.grewwebsiteserver.admin.church

import org.grew.grewwebsiteserver.domain.church.ChurchRepository
import org.springframework.stereotype.Service

@Service
class ChurchAdminService(
    private val churchRepository: ChurchRepository
) {

    fun addChurch(churchInfo: PostChurchRequestDto): PostChurchResponseDto {
        val church = churchInfo.toEntity()
        val savedChurch = churchRepository.save(church)
        return PostChurchResponseDto.from(savedChurch)
    }

    fun updateChurch(id: Long, churchInfo: PostChurchRequestDto): PostChurchResponseDto {
        val church = churchRepository.findById(id).orElseThrow {
            NoSuchElementException("해당 ID의 교회를 찾을 수 없습니다: $id")
        }

        // 기존 Church 객체에 필드 업데이트 (setter나 copy 방식 활용)
        val updatedChurch = church.apply {
            churchInfo.name?.let { name = it }
            churchInfo.pastor_name?.let { pastorName = it }
            churchInfo.website_url?.let { websiteUrl = it }
            churchInfo.address?.let { address = it }
            churchInfo.email?.let { email = it }
            churchInfo.phone?.let { phone = it }
            churchInfo.description?.let { description = it }
            churchInfo.worship_times?.let { worshipTimes = it }
            churchInfo.location_lat?.let { locationLat = it }
            churchInfo.location_lng?.let { locationLng = it }
        }

        val saved = churchRepository.save(updatedChurch)
        return PostChurchResponseDto.from(saved)
    }

    fun deleteChurch(id: Long) {
        val church = churchRepository.findById(id).orElseThrow {
            NoSuchElementException("해당 ID의 교회를 찾을 수 없습니다: $id")
        }
        churchRepository.delete(church)
    }

}