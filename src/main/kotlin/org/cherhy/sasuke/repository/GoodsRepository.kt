package org.cherhy.sasuke.repository

import org.cherhy.sasuke.model.GoodsDocument
import org.cherhy.sasuke.model.GoodsDocumentId
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository


interface GoodsRepository: ElasticsearchRepository<GoodsDocument, GoodsDocumentId> {
    fun findAllByName(name: String): List<GoodsDocument>
    fun findAllByNameContainingIgnoreCase(name: String): List<GoodsDocument>
}