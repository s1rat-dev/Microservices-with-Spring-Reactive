package com.example.productapi.productmanager

import com.example.productapi.productmanager.dto.ProductRequest
import com.example.productapi.productmanager.dto.ProductResponse
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping("/products")
class ProductController(val productService: ProductService) {


    @GetMapping()
    suspend fun getAll() : Flow<ProductResponse?> = productService.getAll()

    @GetMapping("{id}")
    suspend fun findWithId(@PathVariable("id") productId : UUID) : ProductResponse? =
            productService.getById(productId)

    @PostMapping()
    suspend fun addProduct(@RequestBody @Valid productRequest: ProductRequest) : ResponseEntity<String> =
            productService.addProduct(productRequest)


}