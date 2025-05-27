package org.grew.grewwebsiteserver.admin.church

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.grew.grewwebsiteserver.domain.auth.kakao.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/churches")
class ChruchAdminController(
    private val churchAdminService: ChurchAdminService
) {

    @PostMapping
    @Operation(
        summary = "교회 등록",
        description = "새로운 교회를 등록합니다.",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "등록할 교회 정보",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PostChurchRequestDto::class),
                    examples = [
                        ExampleObject(
                            name = "현진교회 예시",
                            summary = "예시 요청 본문",
                            value = """
                            {
                              "name": "현진교회",
                              "pastor_name": "박현진",
                              "website_url": "http://hyunjin.or.kr",
                              "address": "경기도 안양시 동안구 흥안대로 519",
                              "email": "hyunjin@church.or.kr",
                              "phone": "031-123-4567",
                              "description": "사랑과 말씀으로 세워지는 공동체입니다.",
                              "worship_times": "주일 11:00, 수요예배 19:30",
                              "location_lat": 37.401859,
                              "location_lng": 126.976937
                            }
                            """
                        )
                    ]
                )
            ]
        ),
        security = [SecurityRequirement(name = "Authorization")]
    )
    // @PreAuthorize("hasRole('ADMIN')")
    fun addChruch(@RequestBody churchInfo: PostChurchRequestDto): ResponseEntity<ApiResponse<PostChurchResponseDto>> {
        val churchResponse = churchAdminService.addChurch(churchInfo)
        return ResponseEntity.ok(
            ApiResponse(
                message = "교회 등록 성공",
                data = churchResponse
            )
        )
    }


    @PatchMapping("/{id}")
    @Operation(
        summary = "교회 정보 수정",
        description = "기존 교회 정보를 수정합니다.",
        parameters = [
            Parameter(
                name = "id",
                `in` = ParameterIn.PATH,
                description = "수정할 교회의 ID",
                required = true,
                example = "1"
            )
        ],
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "수정할 교회 정보",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PostChurchRequestDto::class),
                    examples = [
                        ExampleObject(
                            name = "현진교회 수정 예시",
                            summary = "예시 요청 본문",
                            value = """
                            {
                              "name": "현진교회",
                              "pastor_name": "박현진",
                              "website_url": "http://hyunjin.or.kr",
                              "address": "경기도 안양시 동안구 흥안대로 519",
                              "email": "hyunjin@church.or.kr",
                              "phone": "031-123-4567",
                              "description": "변경된 설명입니다.",
                              "worship_times": "주일 10:30, 금요기도회 20:00",
                              "location_lat": 37.401859,
                              "location_lng": 126.976937
                            }
                            """
                        )
                    ]
                )
            ]
        ),
        security = [SecurityRequirement(name = "Authorization")]
    )
    // @PreAuthorize("hasRole('ADMIN')")
    fun updateChurch(
        @PathVariable id: Long,
        @RequestBody churchInfo: PostChurchRequestDto
    ): ResponseEntity<ApiResponse<PostChurchResponseDto>> {
        val updatedChurch = churchAdminService.updateChurch(id, churchInfo)
        return ResponseEntity.ok(
            ApiResponse(
                message = "교회 정보 수정 성공",
                data = updatedChurch
            )
        )
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "교회 삭제",
        description = "지정한 ID의 교회를 삭제합니다.",
        parameters = [
            Parameter(
                name = "id",
                `in` = ParameterIn.PATH,
                description = "삭제할 교회의 ID",
                required = true,
                example = "1"
            )
        ],
        security = [SecurityRequirement(name = "Authorization")]
    )
    // @PreAuthorize("hasRole('ADMIN')")
    fun deleteChurch(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {
        churchAdminService.deleteChurch(id)
        return ResponseEntity.ok(
            ApiResponse(
                message = "교회 삭제 성공",
                data = Unit
            )
        )
    }

}