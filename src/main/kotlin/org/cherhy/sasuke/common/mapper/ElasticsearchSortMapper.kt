package org.cherhy.sasuke.common.mapper

import org.cherhy.sasuke.common.constant.ElasticsearchDomain
import org.cherhy.sasuke.common.constant.GoodsSort
import org.cherhy.sasuke.common.constant.SortType
import org.cherhy.sasuke.common.constant.UserSort

object ElasticsearchSortMapper {
    fun convert(domain: ElasticsearchDomain, sortValue: String): SortType =
        when (domain) {
            ElasticsearchDomain.GOODS -> GoodsSort.valueOfOrDefault(sortValue)
            ElasticsearchDomain.USER -> UserSort.valueOfOrDefault(sortValue)
        }
}