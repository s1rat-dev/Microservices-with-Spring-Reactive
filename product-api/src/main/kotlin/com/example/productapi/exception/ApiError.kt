package com.example.productapi.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiError(
        val message: String?,
        val status: HttpStatus,
        val timestamp: LocalDateTime = LocalDateTime.now()
)
