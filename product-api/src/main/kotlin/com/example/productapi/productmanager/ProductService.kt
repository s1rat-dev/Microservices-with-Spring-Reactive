package com.example.productapi.productmanager

import com.example.productapi.exception.ProductAlreadyExistException
import com.example.productapi.exception.ProductNotFoundException
import com.example.productapi.productmanager.dto.ProductRequest
import com.example.productapi.productmanager.dto.ProductResponse
import com.example.productapi.productmanager.mapper.ProductToResponseMapper
import com.example.productapi.productmanager.mapper.RequestToProductMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class ProductService(val productRepository: ProductRepository,
                     val productToResponseMapper: ProductToResponseMapper,
                     val requestToProductMapper: RequestToProductMapper) {

    fun getAll() : Flow<ProductResponse?> {
        return productRepository.findAll().map {
            productToResponseMapper.convert(it)
        }
    }

    suspend fun getById(productId : UUID) : ProductResponse? {

        val product: Product? = productRepository.findById(productId)
        if (product === null)
            throw ProductNotFoundException()

        return productToResponseMapper.convert(product)
    }

    suspend fun isProductAlreadyExist(productName: String,productPrice: BigDecimal) : Product? {
        return productRepository.isProductAlreadyExist(productName,productPrice)
    }
    suspend fun addProduct(productRequest: ProductRequest) : ProductResponse {

        val product : Product? = isProductAlreadyExist(productRequest.name!!,productRequest.price!!)
        if (product != null)
            throw ProductAlreadyExistException()

        return productToResponseMapper
                .convert(productRepository
                        .save(requestToProductMapper
                                .convert(productRequest)!!))!!

    }

}