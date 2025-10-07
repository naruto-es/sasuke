package org.cherhy.sasuke.common.extension

import org.cherhy.sasuke.dsl.ElasticSearchBuilder
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.CriteriaQuery

inline fun <reified T, R> ElasticsearchOperations.query(
    elasticSearchBuilder: ElasticSearchBuilder,
): SearchResponse<T> {
    val query = CriteriaQuery(elasticSearchBuilder.rootCriteria)
    val searchHits = this.search(query, T::class.java)
    val contents: List<T> = searchHits.searchHits.map { it.content }

    return SearchResponse(
        contents = contents,
        totalHits = searchHits.totalHits,
    )
}

inline fun <reified T, R> ElasticsearchOperations.query(
    elasticSearchBuilder: ElasticSearchBuilder,
    pageable: Pageable,
): SearchResponse<T> {
    val query = CriteriaQuery(elasticSearchBuilder.rootCriteria).setPageable<CriteriaQuery>(pageable)
    val searchHits = this.search(query, T::class.java)
    val contents: List<T> = searchHits.searchHits.map { it.content }

    return SearchResponse(
        contents = contents,
        totalHits = searchHits.totalHits,
    )
}

data class SearchResponse<T>(
    val contents: List<T>,
    val totalHits: Long,
)