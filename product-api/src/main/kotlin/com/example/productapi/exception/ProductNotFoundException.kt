package com.example.productapi.exception

class ProductNotFoundException(message: String = "Comment not found.") : RuntimeException(message) {
}