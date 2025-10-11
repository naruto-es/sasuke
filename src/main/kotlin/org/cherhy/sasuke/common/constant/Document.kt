package org.cherhy.sasuke.common.constant

object Document {
    object Index {
        const val GOODS = "goods"
        const val USER = "user"
    }
    const val SCORE = "_score"
}

enum class ElasticsearchDomain {
    GOODS,
    USER,
}