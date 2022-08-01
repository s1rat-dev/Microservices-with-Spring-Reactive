package com.example.productapi.productmanager.dto

import org.hibernate.validator.constraints.Range
import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ProductRequest(
        @field:NotEmpty(message = "Name can not be empty.")
        val name: String?,
        @field:NotNull(message= "Price can not be empty")
        @field:Range(min = 1, message = "Price can not be 0")
        val price: BigDecimal?,
        @field:NotNull(message= "Stock can not be empty")
        @field:Range(min = 1, message = "Stock can not be 0")
        val stock: Int?,
)

