package org.cherhy.sasuke.common.extension

import org.cherhy.sasuke.dsl.ElasticSearchBuilder
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

inline fun <reified T, R> ElasticsearchRepository<T, R>.query(
    elasticSearchBuilder: ElasticSearchBuilder,
    operations: ElasticsearchOperations,
): SearchResponse<T> {
    val query = CriteriaQuery(elasticSearchBuilder.criteria)
    val searchHits = operations.search(query, T::class.java)
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