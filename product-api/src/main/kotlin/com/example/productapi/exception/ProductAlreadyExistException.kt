package com.example.productapi.exception

class ProductAlreadyExistException(message: String = "This product already exist.") : RuntimeException(message) {
}