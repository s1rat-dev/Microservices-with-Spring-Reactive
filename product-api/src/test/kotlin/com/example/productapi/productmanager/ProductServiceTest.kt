package com.example.productapi.productmanager

import com.example.productapi.exception.ProductAlreadyExistException
import com.example.productapi.exception.ProductNotFoundException
import com.example.productapi.productmanager.dto.ProductRequest
import com.example.productapi.productmanager.dto.ProductResponse
import com.example.productapi.productmanager.mapper.ProductToResponseMapper
import com.example.productapi.productmanager.mapper.RequestToProductMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

internal class ProductServiceTest {

    private val productRepository       : ProductRepository         = mockk<ProductRepository>()
    private val productToResponseMapper : ProductToResponseMapper   = mockk<ProductToResponseMapper>()
    private val requestToProductMapper  : RequestToProductMapper    = mockk<RequestToProductMapper>()
    private val productService          : ProductService            = ProductService(
                                                                                productRepository,
                                                                                productToResponseMapper,
                                                                                requestToProductMapper)

    @Test
    fun `when ProductService#getAll is called when database has records`() = runBlocking {
        //Given
        val productId               = UUID.randomUUID()
        val productCreationInstant  = LocalDateTime.now()
        val productPrice            = BigDecimal.valueOf(1000.0)
        val product                 = Product(productId, "Product", productPrice, 100, productCreationInstant)
        val productResponse         = ProductResponse(productId, "Product", productPrice, 100)
        val productFlow             = flowOf(product, product, product)
        val expected                = listOf<ProductResponse>(productResponse, productResponse, productResponse)

        coEvery {
            productRepository.findAll()
        } returns productFlow

        coEvery {
            productToResponseMapper.convert(product)
        } returnsMany expected


        //When
        val actual = productService.getAll().toList()

        //Then
        assertEquals(expected, actual)
        coVerify {
            productRepository.findAll()
        }
        coVerify(exactly = 3) {
            productToResponseMapper.convert(product)
        }
    }

    @Test
    fun `when ProductService#getAll is called when database has no records`() = runBlocking {
        //Given
        val expected = emptyList<ProductResponse>()

        coEvery {
            productRepository.findAll()
        } returns emptyFlow()


        //When
        val actual = productService.getAll().toList()

        //Then
        assertEquals(expected, actual)
        coVerify {
            productRepository.findAll()
        }


    }

    @Test
    fun `when ProductService#getById is called with exist id`() = runBlocking {
        //Given
        val productId               = UUID.randomUUID()
        val productCreatedInstant   = LocalDateTime.now()
        val product                 = Product(productId, "Product", BigDecimal.ONE, 100, productCreatedInstant)
        val expected                = ProductResponse(productId, "Product", BigDecimal.ONE, 100)

        coEvery {
            productRepository.findById(productId)
        } returns product

        coEvery {
            productToResponseMapper.convert(product)
        } returns expected

        //When
        val actual = productService.getById(productId)

        //Then
        assertEquals(expected, actual)
        coVerifySequence {
            productRepository.findById(productId)
            productToResponseMapper.convert(product)
        }

    }

    @Test
    fun `when ProductService#getById is called with not found id`() = runBlocking {
        //Given
        val productId = UUID.randomUUID()
        val expected = null

        coEvery {
            productRepository.findById(productId)
        } returns expected

        //When-Then
        assertThrows<ProductNotFoundException> {
            productService.getById(productId)
        }
        coVerify {
            productRepository.findById(productId)
        }
    }

    @Test
    fun `when ProductService#isProductAlreadyExist is called with valid arguments`() = runBlocking {
        //Given
        val productName     = "Product"
        val productPrice    = BigDecimal.ONE
        val expected        = Product(UUID.randomUUID(), productName, productPrice, 100, LocalDateTime.now())

        coEvery {
            productRepository.isProductAlreadyExist(productName,productPrice)
        } returns expected

        //When
        val actual = productService.isProductAlreadyExist(productName,productPrice)

        //Then
        assertEquals(expected,actual)
        coVerify {
            productRepository.isProductAlreadyExist(productName,productPrice)
        }
    }

    @Test
    fun `when ProductService#isProductAlreadyExist is called with not valid arguments`() = runBlocking {
        //Given
        val productName     = "Product"
        val productPrice    = BigDecimal.ONE
        val expected        = null

        coEvery {
            productRepository.isProductAlreadyExist(productName,productPrice)
        } returns expected

        //When
        val actual = productService.isProductAlreadyExist(productName,productPrice)

        //Then
        assertEquals(expected,actual)
        coVerify {
            productRepository.isProductAlreadyExist(productName,productPrice)
        }
    }

    @Test
    fun `when ProductService#addProduct is called with valid request`() = runBlocking {
        //Given
        val productId               = UUID.randomUUID()
        val productPrice            = BigDecimal.ONE
        val productCreatedInstant   = LocalDateTime.now()
        val productRequest          = ProductRequest("Product",productPrice,100)
        val product                 = Product(productId,"Product",productPrice,100,productCreatedInstant)
        val expected                = ProductResponse(productId,"Product",productPrice,100)

        coEvery {
            productService.isProductAlreadyExist("Product",productPrice)
        } returns null

        coEvery {
            requestToProductMapper.convert(productRequest)
        } returns product

        coEvery {
            productRepository.save(product)
        } returns product

        coEvery {
            productToResponseMapper.convert(product)
        } returns expected


        //When
        val actual = productService.addProduct(productRequest)

        //Then
        assertEquals(expected,actual)
        coVerifySequence {
            productService.isProductAlreadyExist("Product",productPrice)
            requestToProductMapper.convert(productRequest)
            productRepository.save(product)
            productToResponseMapper.convert(product)
        }
    }

    @Test
    fun `when ProductService#addProduct is called with already exist product's request`() = runBlocking {
        //Given
        val productPrice    = BigDecimal.ONE
        val productRequest  = ProductRequest("Product",productPrice,100)
        val product         = Product(UUID.randomUUID(),"Product",productPrice,100, LocalDateTime.now())

        coEvery {
            productRepository.isProductAlreadyExist("Product",productPrice)
        } returns product

        //When-Then
        assertThrows<ProductAlreadyExistException> {
            productService.addProduct(productRequest)
        }
        coVerify {
            productRepository.isProductAlreadyExist("Product",productPrice)
        }
    }

}