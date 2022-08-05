package com.example.productapi.productmanager

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.math.BigDecimal
import java.util.UUID

interface ProductRepository : CoroutineCrudRepository<Product,UUID?> {

    @Query("SELECT * FROM products WHERE name= :name and price= :price")
    suspend fun isProductAlreadyExist(name: String, price: BigDecimal) : Product?

}