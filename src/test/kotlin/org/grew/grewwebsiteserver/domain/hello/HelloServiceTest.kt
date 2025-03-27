package org.grew.grewwebsiteserver.domain.hello

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HelloServiceTest {

    @Test
    fun hello() {
        val helloService = HelloService()
        assertEquals("Hello, World!", helloService.hello())
    }
}