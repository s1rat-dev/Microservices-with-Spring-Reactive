package com.example.commentapi.commentmanager

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CommentRepository : CoroutineCrudRepository<Comment,UUID?> {


    @Query("SELECT * FROM comments WHERE product_id= :productId AND text= :text")
    suspend fun isCommentAlreadyExist(productId: UUID, text: String) : Comment?


}