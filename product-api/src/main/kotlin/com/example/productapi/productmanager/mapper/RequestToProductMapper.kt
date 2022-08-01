package com.example.productapi.productmanager.mapper

import com.example.productapi.productmanager.dto.ProductRequest
import com.example.productapi.productmanager.Product
import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter

@Mapper
interface RequestToProductMapper : Converter<ProductRequest, Product>