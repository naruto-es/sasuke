package org.cherhy.sasuke.common.converter

import org.cherhy.sasuke.common.constant.Document.SCORE
import org.cherhy.sasuke.common.constant.GoodsSort
import org.cherhy.sasuke.common.constant.SortType
import org.cherhy.sasuke.common.constant.UserSort
import org.cherhy.sasuke.model.GoodsDocument
import org.springframework.data.domain.Sort
import org.springframework.data.mapping.toDotPath

object ElasticsearchSortConverter {
    fun convert(sort: SortType): List<Sort.Order> {
        return when (sort) {
            is GoodsSort -> convertGoods(sort)
            is UserSort -> convertUser(sort)
        }
    }

    private fun convertGoods(sort: GoodsSort): List<Sort.Order> = when (sort) {
        GoodsSort.RECOMMEND -> listOf(
            Sort.Order.desc(SCORE),
            Sort.Order.desc(GoodsDocument::createdAt.toDotPath())
        )

        GoodsSort.NEW -> listOf(
            Sort.Order.desc(GoodsDocument::createdAt.toDotPath())
        )

        GoodsSort.PRICE_ASC -> listOf(
            Sort.Order.asc(GoodsDocument::price.toDotPath()),
            Sort.Order.desc(SCORE)
        )

        GoodsSort.PRICE_DESC -> listOf(
            Sort.Order.desc(GoodsDocument::price.toDotPath()),
            Sort.Order.desc(SCORE)
        )

        GoodsSort.MANY_REVIEW -> listOf(
            Sort.Order.desc(GoodsDocument::reviewCount.toDotPath()),
            Sort.Order.desc(SCORE)
        )
    }

    private fun convertUser(sort: UserSort): List<Sort.Order> = when (sort) {
        UserSort.JOINED_AT -> listOf(Sort.Order.desc("joinedAt"))
        UserSort.NAME_ASC -> listOf(Sort.Order.asc("name"))
    }
}