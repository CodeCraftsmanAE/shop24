package de.imedia24.shop.domain.product

import de.imedia24.shop.db.entity.ProductEntity
import java.math.BigDecimal
import java.time.ZonedDateTime

data class ProductRequest(
        val sku: String,
        val name: String,
        val description: String?,
        val price: BigDecimal,
        val stock: Int
) {
    companion object {
        fun ProductRequest.toProductEntity() = ProductEntity(
                sku = sku,
                name = name,
                description = description,
                price = price,
                stock = stock,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
        )
    }
}

