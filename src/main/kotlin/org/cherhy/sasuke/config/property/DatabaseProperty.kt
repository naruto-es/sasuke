package org.cherhy.sasuke.config.property


import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.datasource")
data class DatabaseProperty(
    var url: String,
    var username: String,
    var password: String,
)