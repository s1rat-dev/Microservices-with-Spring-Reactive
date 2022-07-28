package com.example.productapi.productmanager

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table("products")
data class Product(
        @Id
        val id: UUID?,
        val name : String,
        val price : BigDecimal,
        val stock : Int,
        @Column("creation_date")
        @CreatedDate
        val creationDate : LocalDateTime?
        ) {}
