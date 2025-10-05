package org.cherhy.sasuke.common.constant

object ApiUrl {
    const val BASE = "/api"
    const val DEFAULT_VERSION = "/v1"

    object Goods {
        const val DOMAIN_NAME = "goods"
        const val CREATE_GOODS = "${BASE}${DEFAULT_VERSION}/$DOMAIN_NAME"
    }
}