package com.example.productapi.productmanager.dto

import java.math.BigDecimal
import java.util.*

data class ProductResponse(
        val id: UUID?,
        val name: String,
        val price: BigDecimal,
        val stock: Int

        )
