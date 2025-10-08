package org.cherhy.sasuke.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "elasticsearch")
data class ElasticSearchProperty(
    val host: String,
    val port: Int,
) {
    val uri: String
        get() = "$host:$port"
}