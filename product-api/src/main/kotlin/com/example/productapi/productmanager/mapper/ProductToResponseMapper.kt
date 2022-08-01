package com.example.productapi.productmanager.mapper

import com.example.productapi.productmanager.Product
import com.example.productapi.productmanager.dto.ProductResponse
import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter

@Mapper
interface ProductToResponseMapper : Converter<Product,ProductResponse>