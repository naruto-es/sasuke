package org.cherhy.sasuke

import org.cherhy.sasuke.common.constant.ApiUrl.Goods.CREATE_GOODS
import org.cherhy.sasuke.model.GoodsDocument
import org.cherhy.sasuke.model.GoodsDocumentId
import org.cherhy.sasuke.repository.GoodsRepository
import org.springframework.data.elasticsearch.core.RefreshPolicy
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GoodsController(
    private val goodsRepository: GoodsRepository,
) {
    @PostMapping(CREATE_GOODS)
    fun createGoods() {
        val dummy = GoodsDocument(
            id = GoodsDocumentId(),
            name = "Sample Goods",
            price = 1000.toBigDecimal(),
            description = "This is a sample goods description."
        )

        goodsRepository.save(dummy, RefreshPolicy.WAIT_UNTIL)
    }
}