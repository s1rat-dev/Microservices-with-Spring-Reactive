package com.example.productapi.productmanager

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductService(val productRepository: ProductRepository,
                     val productConverter: ProductConverter) {

    suspend fun getAll() : Flow<Product> {
        return productRepository.findAll()
    }

    suspend fun getById(productId : UUID) : Product? {
        return productRepository.findById(productId)
    }

    suspend fun addProduct(productRequest: CreateProductRequest) {
        productRepository.save(productConverter.convert(productRequest)!!)
    }

}