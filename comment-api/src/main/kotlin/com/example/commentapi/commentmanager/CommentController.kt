package com.example.commentapi.commentmanager

import com.example.commentapi.commentmanager.dto.CommentRequest
import com.example.commentapi.commentmanager.dto.CommentResponse
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/comments")
class CommentController(val commentService: CommentService) {

    @GetMapping()
    suspend fun getAll() : Flow<CommentResponse?> = commentService.getAll()



    @GetMapping("/{id}")
    suspend fun findWithId(@PathVariable("id") productId : UUID) : CommentResponse? =
            commentService.getById(productId)



    @PostMapping()
    suspend fun addComment(@Valid @RequestBody commentRequest: CommentRequest) : ResponseEntity<String> =
            commentService.addComment(commentRequest)



}