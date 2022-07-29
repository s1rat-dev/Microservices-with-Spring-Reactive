package com.example.bannerapi.exception

class IdNotExistException(message: String = "ID not found.") : RuntimeException(message) {
}