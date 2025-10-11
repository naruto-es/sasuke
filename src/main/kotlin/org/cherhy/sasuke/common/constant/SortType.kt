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
        @JvmStatic
        val DEFAULT = RECOMMEND

        @JvmStatic
        fun valueOfOrDefault(sortValue: String) = entries.find { it.name == sortValue } ?: DEFAULT
    }
}

enum class UserSort : SortType {
    JOINED_AT,
    NAME_ASC
    ;

    companion object {
        @JvmStatic
        val DEFAULT = JOINED_AT

        @JvmStatic
        fun valueOfOrDefault(sortValue: String) = entries.find { it.name == sortValue } ?: DEFAULT
    }
}