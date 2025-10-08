package org.cherhy.sasuke.repository

import org.cherhy.sasuke.common.extension.search
import org.cherhy.sasuke.common.model.SearchResponse
import org.cherhy.sasuke.dsl.elasticSearch
import org.cherhy.sasuke.model.GoodsDocument
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZonedDateTime

interface GoodsNativeRepository {
    fun findAllByCreatedAtBetween(startedAt: ZonedDateTime, endedAt: ZonedDateTime): SearchResponse<GoodsDocument>
    fun findAll(
        priceRange: ClosedRange<BigDecimal>?,
        name: String?,
        dateRange: ClosedRange<LocalDate>?,
        descriptionContains: String?,
    ): SearchResponse<GoodsDocument>
}

@Repository
class GoodsNativeRepositoryImpl(
    private val elasticsearchTemplate: ElasticsearchTemplate,
) : GoodsNativeRepository {
    override fun findAllByCreatedAtBetween(
        startedAt: ZonedDateTime,
        endedAt: ZonedDateTime,
    ): SearchResponse<GoodsDocument> {
        val query = elasticSearch {
            GoodsDocument::createdAt range startedAt..endedAt
        }
        return elasticsearchTemplate.search<GoodsDocument>(query)
    }

    override fun findAll(
        priceRange: ClosedRange<BigDecimal>?,
        name: String?,
        dateRange: ClosedRange<LocalDate>?,
        descriptionContains: String?,
    ): SearchResponse<GoodsDocument> {
        val query = elasticSearch {
            must {
                priceRange?.let { GoodsDocument::price range it }
                name?.let { GoodsDocument::name match it }
                dateRange?.let { GoodsDocument::createdAt range dateRange }
                descriptionContains?.let { GoodsDocument::description term it }
                GoodsDocument::createdAt gt ZonedDateTime.now().minusYears(1)
            }
        }
        return elasticsearchTemplate.search<GoodsDocument>(query)
    }
}