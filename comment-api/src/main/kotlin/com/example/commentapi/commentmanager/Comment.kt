package com.example.commentapi.commentmanager

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("comments")
data class Comment(

    @Id
    val id : UUID?,
    @Column("product_id")
    val productId : UUID,
    @Column("creation_date")
    @CreatedDate
    val creationDate : LocalDateTime?,
    @Column("text")
    val text : String

)


