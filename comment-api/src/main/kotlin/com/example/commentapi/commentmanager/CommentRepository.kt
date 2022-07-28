package com.example.commentapi.commentmanager

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CommentRepository : CoroutineCrudRepository<Comment,UUID?> {


}