package com.example.commentapi.commentmanager

import java.time.LocalDateTime
import java.util.UUID

data class CreateCommentRequest(
        val productId: UUID?,
        val text: String?,
)
