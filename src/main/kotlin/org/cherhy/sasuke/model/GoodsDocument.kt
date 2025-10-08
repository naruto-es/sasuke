package org.cherhy.sasuke.model

import com.fasterxml.jackson.annotation.JsonCreator
import org.cherhy.sasuke.common.constant.DocumentId
import org.cherhy.sasuke.common.constant.DocumentIdGenerator
import org.cherhy.sasuke.config.constant.Analyzer.KOREAN_ANALYZER
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.math.BigDecimal
import java.time.ZonedDateTime

@JvmInline
value class GoodsDocumentId private constructor(val value: String) {
    init {
        require(value.matches(DocumentId.REGEX)) { DocumentId.VALIDATE_MESSAGE.format(value) }
    }

    companion object {
        operator fun invoke() = of(DocumentIdGenerator.generate())
        operator fun invoke(value: String) = of(value)

        @JvmStatic
        @JsonCreator
        fun of(value: String) = GoodsDocumentId(value.trim())
    }
}

@Document(indexName = "goods")
data class GoodsDocument(
    @Id
    @field:Field(type = FieldType.Keyword, name = DocumentId.NAME)
    val id: GoodsDocumentId,

    @field:Field(type = FieldType.Keyword)
    val name: String,

    @field:Field(type = FieldType.Double)
    val price: BigDecimal,

    @field:Field(type = FieldType.Text, analyzer = KOREAN_ANALYZER, searchAnalyzer = KOREAN_ANALYZER)
    val description: String,

    @field:Field(type = FieldType.Integer)
    val reviewCount: Int,

    @field:Field(type = FieldType.Date)
    val createdAt: ZonedDateTime,
)