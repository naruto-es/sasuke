package org.cherhy.sasuke.common.extension

import org.cherhy.sasuke.common.model.SearchResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.CriteriaQuery

inline fun <reified T> ElasticsearchOperations.search(
    query: CriteriaQuery,
): SearchResponse<T> {
    val searchHits = this.search(query, T::class.java)
    val contents: List<T> = searchHits.searchHits.map { it.content }

    return SearchResponse(
        contents = contents,
        totalHits = searchHits.totalHits,
    )
}

inline fun <reified T> ElasticsearchOperations.search(
    query: CriteriaQuery,
    pageable: Pageable,
): SearchResponse<T> {
    val searchHits = this.search(query.setPageable<CriteriaQuery>(pageable), T::class.java)
    val contents: List<T> = searchHits.searchHits.map { it.content }

    return SearchResponse(
        contents = contents,
        totalHits = searchHits.totalHits,
    )
}