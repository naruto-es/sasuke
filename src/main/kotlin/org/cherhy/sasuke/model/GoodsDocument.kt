package org.cherhy.sasuke.model

import com.fasterxml.jackson.annotation.JsonCreator
import org.cherhy.sasuke.config.constant.Analyzer.KOREAN_ANALYZER
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.math.BigDecimal

@JvmInline
value class GoodsDocumentId private constructor(val value: String) {
    init {
        require(value.matches(REGEX)) { "${value}는 Elasticsearch Document ID로 허용되지 않는 ID 형식입니다." }
    }

    operator fun invoke(value: String) = of(value)

    companion object {
        private val REGEX = "^[a-zA-Z0-9_-]{1,64}$".toRegex()

        @JvmStatic
        @JsonCreator
        fun of(value: String) = GoodsDocumentId(value.trim())
    }
}

@Document(indexName = "goods")
data class GoodsDocument(
    @Id
    @field:Field(type = FieldType.Keyword, name = "_id")
    val id: GoodsDocumentId,

    @field:Field(type = FieldType.Keyword)
    val name: String,

    @field:Field(type = FieldType.Double)
    val price: BigDecimal,

    @field:Field(type = FieldType.Text, analyzer = KOREAN_ANALYZER, searchAnalyzer = KOREAN_ANALYZER)
    val description: String,
)