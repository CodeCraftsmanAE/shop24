import de.imedia24.shop.controller.ProductController
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.eq
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class ProductControllerTest {

    private val productService: ProductService = mock()
    private lateinit var productController: ProductController

    @BeforeEach
    fun setUp() {
        productController = ProductController(productService)
    }

    @Test
    fun `findProductBySku returns product when found`() {
        // Arrange
        val sku = "12345"
        val productResponse = ProductResponse(sku, "Product Name", "Product Description", BigDecimal.valueOf(12.5), 100)
        whenever(productService.findProductBySku(eq(sku))).thenReturn(productResponse)

        // Act
        val response = productController.findProductBySku(sku)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(productResponse, response.body)
    }

    @Test
    fun `findProductBySku returns not found when product does not exist`() {
        // Arrange
        val sku = "12345"
        whenever(productService.findProductBySku(eq(sku))).thenReturn(null)

        // Act
        val response = productController.findProductBySku(sku)

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals(null, response.body)
    }
}
