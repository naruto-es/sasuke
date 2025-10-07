package org.cherhy.sasuke.dsl

import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import kotlin.reflect.KProperty1

@DslMarker
annotation class ElasticSearchDSL

@ElasticSearchDSL
class ElasticSearchBuilder {
    val criteria = Criteria()

    infix fun <T : Any, R> KProperty1<T, R>.match(value: R) {
        val newCriteria = Criteria(this.name).`is`(value as Any)
        appendCriteria(newCriteria)
    }

    infix fun <T : Any> KProperty1<T, Int>.range(range: IntRange) {
        val newCriteria = Criteria(this.name)
            .greaterThanEqual(range.first)
            .lessThanEqual(range.last)
        appendCriteria(newCriteria)
    }

    infix fun <T : Any, R> KProperty1<T, R>.term(value: R) {
        appendCriteria(
            Criteria(this.name).`is`(value as Any)
        )
    }

    infix fun <T : Any, R> KProperty1<T, R>.gt(value: R) where R : Comparable<R> {
        appendCriteria(Criteria(this.name).greaterThan(value))
    }

    infix fun <T : Any, R> KProperty1<T, R>.gte(value: R) where R : Comparable<R> {
        appendCriteria(Criteria(this.name).greaterThanEqual(value))
    }

    infix fun <T : Any, R> KProperty1<T, R>.lt(value: R) where R : Comparable<R> {
        appendCriteria(Criteria(this.name).lessThan(value))
    }

    infix fun <T : Any, R> KProperty1<T, R>.lte(value: R) where R : Comparable<R> {
        appendCriteria(Criteria(this.name).lessThanEqual(value))
    }

    infix fun and(block: ElasticSearchBuilder.() -> Unit) {
        val nested = ElasticSearchBuilder().apply(block)
        criteria.and(nested.criteria)
    }

    infix fun or(block: ElasticSearchBuilder.() -> Unit) {
        val nested = ElasticSearchBuilder().apply(block)
        criteria.or(nested.criteria)
    }

    private fun appendCriteria(criteria: Criteria) {
        criteria.and(criteria)
    }
}

fun elasticSearch(block: ElasticSearchBuilder.() -> Unit): CriteriaQuery {
    val builder = ElasticSearchBuilder()
    builder.block()
    return CriteriaQuery(builder.criteria)
}