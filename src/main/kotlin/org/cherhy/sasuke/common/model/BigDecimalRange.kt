package org.cherhy.sasuke.common.model

import java.math.BigDecimal

class BigDecimalRange(val first: BigDecimal, val last: BigDecimal) {
    init {
        require(first <= last) { "Invalid range: $first > $last" }
    }
}