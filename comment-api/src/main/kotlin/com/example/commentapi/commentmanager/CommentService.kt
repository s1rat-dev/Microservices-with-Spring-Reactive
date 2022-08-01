package com.example.commentapi.commentmanager

import com.example.commentapi.commentmanager.dto.CommentRequest
import com.example.commentapi.commentmanager.dto.CommentResponse
import com.example.commentapi.commentmanager.mapper.CommentToResponseMapper
import com.example.commentapi.commentmanager.mapper.RequestToCommentMapper
import com.example.commentapi.exception.CommentAlreadyExistException
import com.example.commentapi.exception.CommentNotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CommentService(val commentRepository: CommentRepository,
                     val requestToCommentMapper: RequestToCommentMapper,
                     val commentToResponseMapper: CommentToResponseMapper) {


    suspend fun getAll() : Flow<CommentResponse?> {
        return commentRepository.findAll().map {
            commentToResponseMapper.convert(it)
        }
    }

    suspend fun isCommentAlreadyExist(productId : UUID, text: String) : Comment? =
            commentRepository.isCommentAlreadyExist(productId,text)

    suspend fun getById(commentId : UUID) : CommentResponse? {
        val comment : Comment? = commentRepository.findById(commentId)
        if (comment === null)
            throw CommentNotFoundException()

        return commentToResponseMapper.convert(comment)
    }

    suspend fun addComment(commentRequest: CommentRequest) : ResponseEntity<String> {

        val comment : Comment? = isCommentAlreadyExist(commentRequest.productId,commentRequest.text!!)
        if (comment != null)
            throw CommentAlreadyExistException()

        commentRepository.save(requestToCommentMapper.convert(commentRequest)!!)
        return ResponseEntity.ok().body("Comment created successfully.")
    }


}