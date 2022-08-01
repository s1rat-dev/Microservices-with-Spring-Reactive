package com.example.commentapi.exception

class CommentNotFoundException(message: String = "Comment not found.") : RuntimeException(message) {
}