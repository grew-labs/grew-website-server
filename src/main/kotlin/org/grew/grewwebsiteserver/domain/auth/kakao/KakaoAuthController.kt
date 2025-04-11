package org.grew.grewwebsiteserver.domain.auth.kakao

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/oauth/kakao")
class KakaoAuthController(
    private val kakaoAuthService: KakaoAuthService
) {


    // 카카오 로그인 콜백 (인가 코드 수신 → 토큰 발급 → 사용자 정보 획득)
    @GetMapping("/callback")
    fun kakaoCallback(@RequestParam code: String): ResponseEntity<ApiResponse<AuthResponseDto>> {
        val data = kakaoAuthService.kakaoLogin(code)
        val response = ApiResponse(
            message = "로그인 성공: ${data.nickname}",
            data = data
        )
        return ResponseEntity.ok(response)
    }

    // 테스트용 리프레시 엔드포인트 (userId로 access token 재발급)
    @GetMapping("/refresh")
    fun refreshToken(@RequestParam userId: Long): ResponseEntity<ApiResponse<AuthResponseDto>> {
        val newAccessToken = kakaoAuthService.refreshAccessToken(userId)
        val response = ApiResponse(
            message = "새 액세스 토큰: $newAccessToken",
            data = AuthResponseDto(tokenInfo = newAccessToken, nickname = null)
        )
        return ResponseEntity.ok(response)
    }
}

