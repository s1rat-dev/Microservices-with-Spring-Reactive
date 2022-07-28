package com.example.productapi.productmanager

import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter

@Mapper
interface ProductConverter : Converter<CreateProductRequest,Product>{
}