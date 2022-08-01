package com.example.commentapi.commentmanager.mapper

import com.example.commentapi.commentmanager.Comment
import com.example.commentapi.commentmanager.dto.CommentResponse
import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter

@Mapper
interface CommentToResponseMapper : Converter<Comment,CommentResponse>