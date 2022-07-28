package com.example.commentapi.commentmanager

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CommentService(val commentRepository: CommentRepository,
                     val commentConverter: CommentConverter) {


    suspend fun getAll() : Flow<Comment> {
        return commentRepository.findAll()
    }


    suspend fun getById(productId : UUID) : Comment? {
        return commentRepository.findById(productId)
    }

    suspend fun addComment(commentRequest: CreateCommentRequest) {
        commentRepository.save(commentConverter.convert(commentRequest)!!)
    }


}