package com.example.commentapi.commentmanager

import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter


@Mapper
interface CommentConverter  : Converter<CreateCommentRequest,Comment>

