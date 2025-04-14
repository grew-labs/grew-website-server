package org.grew.grewwebsiteserver.domain.church

import org.grew.grewwebsiteserver.domain.auth.kakao.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/churches")
class ChurchController(
    private val churchService: ChurchService
) {

    @GetMapping("/{id}")
    fun getChurch(@PathVariable id: Long): ResponseEntity<ApiResponse<ChurchDetailResponse>> {
        val data = churchService.getChurch(id)
        return ResponseEntity.ok(ApiResponse.success(data))
    }

    @GetMapping
    fun getChurches(): ResponseEntity<ApiResponse<List<ChurchListItem>>> {
        val data = churchService.getChurches()
        return ResponseEntity.ok(ApiResponse.success(data))
    }
}
