package org.grew.grewwebsiteserver.domain.church

import org.springframework.stereotype.Service

@Service
class ChurchService(private val churchRepository: ChurchRepository) {
    fun getChurch(id: Long): ChurchDetailResponse {
        val church = churchRepository.findById(id).orElseThrow { NoSuchElementException("Church not found") }
        return ChurchDetailResponse.from(church)
    }

    fun getChurches(): List<ChurchListItem> {
        return churchRepository.findAll().map { ChurchListItem.from(it) }
    }
}