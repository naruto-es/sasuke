package org.cherhy.sasuke.model

import org.cherhy.sasuke.config.constant.Analyzer.KOREAN_ANALYZER
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.math.BigDecimal

@JvmInline
value class GoodsDocumentId(val value: Long)

@Document(indexName = "goods")
data class GoodsDocument(
    @Id
    @field:Field(type = FieldType.Long)
    val id: GoodsDocumentId,

    @field:Field(type = FieldType.Keyword)
    val name: String,

    @field:Field(type = FieldType.Double)
    val price: BigDecimal,

    @field:Field(type = FieldType.Text, analyzer = KOREAN_ANALYZER, searchAnalyzer = KOREAN_ANALYZER)
    val description: String,
)