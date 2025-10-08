package org.cherhy.sasuke.common.constant

sealed interface SortType

enum class GoodsSort : SortType {
    RECOMMEND,
    NEW,
    PRICE_ASC,
    PRICE_DESC,
    MANY_REVIEW
    ;

    companion object {
        val DEFAULT = RECOMMEND
        fun valueOfOrDefault(sortValue: String) = entries.find { it.name == sortValue } ?: DEFAULT
    }
}

enum class UserSort : SortType {
    JOINED_AT,
    NAME_ASC
    ;

    companion object {
        val DEFAULT = JOINED_AT
        fun valueOfOrDefault(sortValue: String) = entries.find { it.name == sortValue } ?: DEFAULT
    }
}