package com.example.productapi.productmanager

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface ProductRepository : CoroutineCrudRepository<Product,UUID?> {
}