package org.cherhy.sasuke.repository

import org.cherhy.sasuke.model.Goods
import org.cherhy.sasuke.model.GoodsId
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository


interface GoodsRepository: ElasticsearchRepository<Goods, GoodsId> {
    fun findAllByName(name: String): List<Goods>
    fun findAllByNameContainingIgnoreCase(name: String): List<Goods>
}