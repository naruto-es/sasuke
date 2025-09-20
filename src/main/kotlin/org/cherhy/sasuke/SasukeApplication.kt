package org.cherhy.sasuke

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class SasukeApplication

fun main(args: Array<String>) {
    runApplication<SasukeApplication>(*args)
}
