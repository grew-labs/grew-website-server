package org.grew.grewwebsiteserver.domain.hello

import org.springframework.stereotype.Service

@Service
class HelloService {
    fun hello(): String {
        return "Hello, World!"
    }

    fun hello(name: String): String {
        return "Hello, $name!"
    }
}