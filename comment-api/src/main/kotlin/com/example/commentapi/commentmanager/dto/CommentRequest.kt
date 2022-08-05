package com.example.commentapi.commentmanager.dto

import java.util.UUID
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CommentRequest(
        @field:NotNull(message = "UUID can not be null.")
        val productId: UUID?,
        @field:NotEmpty(message = "Text can not be empty.")
        val text: String?,
)
