package org.cherhy.sasuke.common.constant

import java.util.UUID

object DocumentId {
    const val VALIDATE_VALUE: String = "{value}"
    val VALIDATE_MESSAGE = DocumentValidateMessage(
        "Elasticsearch Document ID는 영문 대소문자, 숫자, '-', '_' 문자로만 이루어져야 합니다. ${VALIDATE_VALUE}는 허용되지 않는 ID 형식입니다."
    )

    const val MAX_LENGTH: Int = 64
    val REGEX: Regex = "^[a-zA-Z0-9_-]{1,$MAX_LENGTH}$".toRegex()

    @JvmInline
    value class DocumentValidateMessage(val message: String) {
        fun format(value: String): String = this.message.replace(VALIDATE_VALUE, value)
    }
}

object DocumentIdGenerator {
    fun generate(): String = UUID.randomUUID().toString().substring(0, DocumentId.MAX_LENGTH)
}