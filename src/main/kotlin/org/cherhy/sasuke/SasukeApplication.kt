package org.cherhy.sasuke

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SasukeApplication

fun main(args: Array<String>) {
    runApplication<SasukeApplication>(*args)
}
