package com.example.productapi.productmanager

import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/products")
class ProductController(val productService: ProductService) {


    @GetMapping()
    suspend fun getAll() : Flow<Product> = productService.getAll()

    @GetMapping("{id}")
    suspend fun findWithId(@PathVariable("id") productId : UUID) : Product? = productService.getById(productId)

    @PostMapping()
    suspend fun addProduct(@RequestBody productRequest: CreateProductRequest) {
        productService.addProduct(productRequest)
    }

}