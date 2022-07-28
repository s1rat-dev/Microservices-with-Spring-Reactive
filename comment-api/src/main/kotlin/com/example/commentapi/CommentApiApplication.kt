package com.example.commentapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommentApiApplication

fun main(args: Array<String>) {
    runApplication<CommentApiApplication>(*args)
}
