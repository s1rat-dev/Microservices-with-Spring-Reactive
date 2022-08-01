package com.example.commentapi.exception

class CommentAlreadyExistException(message: String = "This comment already exist.") : RuntimeException(message) {
}