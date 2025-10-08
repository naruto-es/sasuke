package org.cherhy.sasuke.dsl

import org.cherhy.sasuke.common.model.BigDecimalRange
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.reflect.KProperty1

@DslMarker
annotation class ElasticSearchDSL

@ElasticSearchDSL
class ElasticSearchBuilder {
    val rootCriteria = Criteria()

    fun must(block: ElasticSearchBuilder.() -> Unit) {
        val nested = ElasticSearchBuilder().apply(block)
        rootCriteria.and(nested.rootCriteria)
    }

    fun mustNot(block: ElasticSearchBuilder.() -> Unit) {
        val nested = ElasticSearchBuilder().apply(block)
        rootCriteria.and(nested.rootCriteria.not())
    }

    infix fun <T : Any, R> KProperty1<T, R>.match(value: R) {
        val newCriteria = Criteria(this.name).`is`(value as Any)
        rootCriteria.and(newCriteria)
    }

    infix fun <T : Any> KProperty1<T, Int>.range(range: IntRange) {
        val newCriteria = Criteria(this.name)
            .greaterThanEqual(range.first)
            .lessThanEqual(range.last)
        rootCriteria.and(newCriteria)
    }

    infix fun <T : Any> KProperty1<T, BigDecimal>.range(range: BigDecimalRange) {
        val newCriteria = Criteria(this.name)
            .greaterThanEqual(range.first)
            .lessThanEqual(range.last)
        rootCriteria.and(newCriteria)
    }

    infix fun <T : Any> KProperty1<T, ZonedDateTime>.range(range: ClosedRange<ZonedDateTime>) {
        val newCriteria = Criteria(this.name)
            .greaterThanEqual(range.start)
            .lessThanEqual(range.endInclusive)
        rootCriteria.and(newCriteria)
    }

    infix fun <T : Any> KProperty1<T, ZonedDateTime>.range(range: ClosedRange<LocalDate>) {
        val newCriteria = Criteria(this.name)
            .greaterThanEqual(range.start)
            .lessThanEqual(range.endInclusive)
        rootCriteria.and(newCriteria)
    }

    infix fun <T : Any, R> KProperty1<T, R>.term(value: R) {
        rootCriteria.and(
            Criteria(this.name).`is`(value as Any)
        )
    }

    infix fun <T : Any, R> KProperty1<T, R>.terms(values: Collection<R>) {
        rootCriteria.and(
            Criteria(this.name).`in`(values)
        )
    }

    infix fun <T : Any, R> KProperty1<T, R>.exists(exists: Boolean) {
        if (exists) rootCriteria.and(Criteria(this.name).exists())
        else rootCriteria.and(Criteria(this.name).exists().not())
    }

    infix fun <T : Any, R> KProperty1<T, R>.gt(value: R) where R : Comparable<R> {
        rootCriteria.and(
            Criteria(this.name).greaterThan(value)
        )
    }

    infix fun <T : Any, R> KProperty1<T, R>.gte(value: R) where R : Comparable<R> {
        rootCriteria.and(
            Criteria(this.name).greaterThanEqual(value)
        )
    }

    infix fun <T : Any, R> KProperty1<T, R>.lt(value: R) where R : Comparable<R> {
        rootCriteria.and(
            Criteria(this.name).lessThan(value)
        )
    }

    infix fun <T : Any, R> KProperty1<T, R>.lte(value: R) where R : Comparable<R> {
        rootCriteria.and(
            Criteria(this.name).lessThanEqual(value)
        )
    }

    infix fun and(block: ElasticSearchBuilder.() -> Unit) {
        val nested = ElasticSearchBuilder().apply(block)
        rootCriteria.and(nested.rootCriteria)
    }

    infix fun or(block: ElasticSearchBuilder.() -> Unit) {
        val nested = ElasticSearchBuilder().apply(block)
        rootCriteria.or(nested.rootCriteria)
    }
}

@ElasticSearchDSL
fun elasticSearch(block: ElasticSearchBuilder.() -> Unit): CriteriaQuery {
    val builder = ElasticSearchBuilder()
    builder.block()
    return CriteriaQuery(builder.rootCriteria)
}