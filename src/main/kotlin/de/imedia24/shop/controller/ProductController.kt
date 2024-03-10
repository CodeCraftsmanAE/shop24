package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.UpdateProductRequest
import de.imedia24.shop.service.ProductService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)

    @ApiOperation(value = "Find product by sku", notes = "Retrieve product information by providing its sku")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successful retrieval of product information"),
            ApiResponse(code = 404, message = "Product not found for the provided sku")
        ]
    )
    @GetMapping("/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return product?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @ApiOperation(value = "Find products by skus", notes = "Retrieve product list by providing a set of skus")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successful retrieval of product list"),
            ApiResponse(code = 404, message = "Products not found for the provided skus")
        ]
    )
    @GetMapping("", produces = ["application/json;charset=utf-8"])
    fun getProductsBySkus(
        @RequestParam("skus") skus: Set<String>
    ): ResponseEntity<List<ProductResponse>> {
        logger.info("Request for products with skus $skus")
        val products = productService.findProductListBySkus(skus)
        return if (products?.isNotEmpty() == true) {
            ResponseEntity.ok(products)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @ApiOperation(value = "Add Product", notes = "Adds new product")
    @ApiResponses(
        value = [
            ApiResponse(code = 201, message = "Product created successfully"),
            ApiResponse(code = 400, message = "Invalid product data provided")
        ]
    )
    @PostMapping("", consumes = ["application/json;charset=utf-8"])
    fun addProduct(
        @Valid @RequestBody productRequest: ProductRequest
    ): ResponseEntity<ProductResponse> {
        logger.info("Adding product: $productRequest")

        val productResponse = productService.addProduct(productRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse)
    }

    @ApiOperation(value = "Update product", notes = "Updates the name, description, and price of an existing product")
    @ApiResponses(
        value = [
            ApiResponse(code = 204, message = "Product updated successfully"),
            ApiResponse(code = 404, message = "Product not found with the provided sku"),
            ApiResponse(code = 400, message = "Invalid product update data provided")
        ]
    )
    @PatchMapping("/{sku}", consumes = ["application/json;charset=utf-8"])
    fun updateProduct(
        @PathVariable sku: String,
        @Valid @RequestBody updateProductRequest: UpdateProductRequest
    ): ResponseEntity<ProductResponse> {
        logger.info("Request to update product with sku $sku")

        val updatedProduct = productService.updateProduct(sku, updateProductRequest)
        return updatedProduct?.let {
            ResponseEntity.ok(it) // 200 OK with the updated product in the response body
        } ?: ResponseEntity.notFound().build()
    }
}
