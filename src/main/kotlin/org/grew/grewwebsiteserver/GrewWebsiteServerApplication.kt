package org.grew.grewwebsiteserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrewWebsiteServerApplication

fun main(args: Array<String>) {
    runApplication<GrewWebsiteServerApplication>(*args)
}
