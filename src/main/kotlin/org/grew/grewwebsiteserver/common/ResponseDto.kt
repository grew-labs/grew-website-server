package org.grew.grewwebsiteserver.common

import org.springframework.http.ResponseEntity

typealias ResponseDto<T> = ResponseEntity<Response.Body<T>>
