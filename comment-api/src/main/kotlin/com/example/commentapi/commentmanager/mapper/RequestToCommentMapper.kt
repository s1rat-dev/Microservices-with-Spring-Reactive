package com.example.commentapi.commentmanager.mapper

import com.example.commentapi.commentmanager.Comment
import com.example.commentapi.commentmanager.dto.CommentRequest
import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter


@Mapper
interface RequestToCommentMapper  : Converter<CommentRequest, Comment>

