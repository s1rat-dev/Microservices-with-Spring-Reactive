package com.example.commentapi.commentmanager

import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/comments")
class CommentController(val commentService: CommentService) {

    @GetMapping()
    suspend fun getAll() : Flow<Comment> {
        return commentService.getAll()
    }


    @GetMapping("{id}")
    suspend fun findWithId(@PathVariable("id") productId : UUID) : Comment? {
        return commentService.getById(productId)
    }


    @PostMapping()
    suspend fun addComment(@RequestBody commentRequest: CreateCommentRequest) {
        commentService.addComment(commentRequest)
    }


}