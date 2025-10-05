package org.cherhy.sasuke.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SerializerConfig {
    @Bean
    fun objectMapper() =
        ObjectMapper().registerKotlinModule()
}