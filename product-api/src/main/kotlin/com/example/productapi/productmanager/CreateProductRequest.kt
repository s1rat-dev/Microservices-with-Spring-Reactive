package com.example.productapi.productmanager

import java.math.BigDecimal

data class CreateProductRequest(

        val name: String,
        val price: BigDecimal,
        val stock: Int,
)

