package de.imedia24.shop.service

import de.imedia24.shop.controller.ProductController
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductRequest.Companion.toProductEntity
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import de.imedia24.shop.domain.product.UpdateProductRequest
import javassist.NotFoundException
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        val productEntity = productRepository.findBySku(sku)
        return productEntity?.toProductResponse()
    }

    fun findProductListBySkus(skus: Collection<String>): List<ProductResponse>? {
        val productEntities = productRepository.findBySkuIn(skus)
        return productEntities?.stream()?.map { entity -> entity.toProductResponse() }?.collect(Collectors.toList())
    }

    fun addProduct(productRequest: ProductRequest): ProductResponse? {
        val productEntity = productRequest.toProductEntity()
        return productRepository.save(productEntity).toProductResponse()
    }

    fun updateProduct(sku: String, updateProductRequest: UpdateProductRequest): ProductResponse? {
        val originalProduct = productRepository.findBySku(sku)

        val patchedProduct = originalProduct?.copy( // Entity class is immutable
                name = updateProductRequest.name ?: originalProduct.name,
                description = updateProductRequest.description ?: originalProduct.description,
                price = updateProductRequest.price ?: originalProduct.price
        )

                ?: return null
        return productRepository.save(patchedProduct).toProductResponse()
    }
}
