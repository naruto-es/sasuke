package org.cherhy.sasuke.common.model

import org.cherhy.sasuke.common.constant.ElasticsearchDomain
import org.cherhy.sasuke.common.constant.Order
import org.cherhy.sasuke.common.converter.ElasticsearchSortConverter
import org.cherhy.sasuke.common.mapper.ElasticsearchSortMapper
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class SearchRequest(
    val page: Int,
    val size: Int,
    private val sortValue: String,
    private val order: Order = Order.DESC,
) {
    init {
        require(page >= 0) { "page must be greater than or equal to 0" }
        require(size > 0) { "size must be greater than 0" }
    }

    fun toPageable(
        domain: ElasticsearchDomain,
    ): Pageable {
        val sortType = ElasticsearchSortMapper.convert(domain, sortValue)
        val orders = ElasticsearchSortConverter.convert(sortType)
        val newSort = Sort.by(orders)

        return PageRequest.of(
            if (page < 0) 0 else page,
            if (size <= 0) 10 else size,
            newSort,
        )
    }
}

data class SearchResponse<T>(
    val contents: List<T>,
    val totalHits: Long,
)