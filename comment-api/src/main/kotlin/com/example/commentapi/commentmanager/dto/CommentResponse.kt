package com.example.commentapi.commentmanager.dto

import java.util.*

data class CommentResponse(
        val id: UUID?,
        val productId: UUID?,
        val text: String?
)
