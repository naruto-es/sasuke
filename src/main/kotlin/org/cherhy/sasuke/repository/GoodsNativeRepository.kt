package org.cherhy.sasuke.repository

import org.cherhy.sasuke.common.extension.search
import org.cherhy.sasuke.common.model.SearchResponse
import org.cherhy.sasuke.dsl.elasticSearch
import org.cherhy.sasuke.model.GoodsDocument
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
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
    private val elasticsearchOperations: ElasticsearchOperations,
) : GoodsNativeRepository {
    override fun findAllByCreatedAtBetween(
        startedAt: ZonedDateTime,
        endedAt: ZonedDateTime,
    ) =
        elasticsearchOperations.search<GoodsDocument> {
            elasticSearch {
                GoodsDocument::createdAt range startedAt..endedAt
            }
        }

    override fun findAll(
        priceRange: ClosedRange<BigDecimal>?,
        name: String?,
        dateRange: ClosedRange<LocalDate>?,
        descriptionContains: String?,
    ) =
        elasticsearchOperations.search<GoodsDocument> {
            elasticSearch {
                must {
                    priceRange?.let { GoodsDocument::price range it }
                    name?.let { GoodsDocument::name match it }
                    dateRange?.let { GoodsDocument::createdAt range it }
                    descriptionContains?.let { GoodsDocument::description term it }
                    GoodsDocument::createdAt gt ZonedDateTime.now().minusYears(1)
                }
            }
        }
}