package com.example.commentapi.commentmanager.dto

import java.util.UUID
import javax.validation.constraints.NotEmpty

data class CommentRequest(
        val productId: UUID,
        @field:NotEmpty(message = "Text can not be empty.")
        val text: String?,
)
