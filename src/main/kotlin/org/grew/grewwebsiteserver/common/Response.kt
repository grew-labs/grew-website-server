package org.grew.grewwebsiteserver.common

import org.springframework.http.ResponseEntity

class Response {

    data class Body<T>(
        val result: String, // 성공인지, 실패인지 (SUCCESS, FAILURE)
        val data: T?, // 성공일 때 전달되는 결과
        val errorCode: String?, // 실패일 때 전달되는 Code
        val errorMessage: String? // 실패일 때 전달되는 메세지
    )

    companion object {

        // statusCode: 200
        fun <T> success(data: T): ResponseEntity<Body<T>> {
            val body = Body(result = "SUCCESS", data = data, errorCode = null, errorMessage = null)
            return ResponseEntity.ok(body)
        }

        // statusCode: 400
        // errorCode는 클라이언트에게 오류 케이스를 알려줘야 할 필요가 있을 때 사용
        fun <T> failure(errorCode: String = "NOT_DEFINED", errorMessage: String?): ResponseEntity<Body<T>> {
            val body = Body<T>(result = "FAILURE", data = null, errorCode = errorCode, errorMessage = errorMessage)
            return ResponseEntity.status(400).body(body)
        }

        // statusCode: 500
        fun <T> unexpectedException(e: Exception): ResponseEntity<Body<T>> {
            val body = Body<T>(result = "FAILURE", data = null, errorCode = "UNEXPECTED_EXCEPTION", errorMessage = e.message)
            return ResponseEntity.status(500).body(body)
        }
    }
}

typealias ResponseDto<T> = ResponseEntity<Response.Body<T>>
