package com.example.commentapi.commentmanager

import com.example.commentapi.commentmanager.dto.CommentRequest
import com.example.commentapi.commentmanager.dto.CommentResponse
import com.example.commentapi.commentmanager.mapper.CommentToResponseMapper
import com.example.commentapi.commentmanager.mapper.RequestToCommentMapper
import com.example.commentapi.exception.CommentAlreadyExistException
import com.example.commentapi.exception.CommentNotFoundException
import io.mockk.*
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.util.UUID

internal class CommentServiceTest {

    private val commentRepository       : CommentRepository         = mockk()
    private val commentToResponseMapper : CommentToResponseMapper   = mockk()
    private val requestToCommentMapper  : RequestToCommentMapper    = mockk()
    private val commentService          : CommentService            = CommentService(commentRepository,
                                                                                requestToCommentMapper,
                                                                                commentToResponseMapper)


//    private lateinit var commentService : CommentService
//    @BeforeEach
//    fun init() {
//        clearMocks(commentRepository,commentToResponseMapper,requestToCommentMapper)
//        commentService = CommentService(commentRepository,requestToCommentMapper, commentToResponseMapper)
//    }

    @Test
    fun `when CommentService#getAll is called when database has records`() = runBlocking {
        //Given
        val commentIds              = UUID.randomUUID()
        val productIds              = UUID.randomUUID()
        val commentCreatedInstant   = LocalDateTime.now()
        val text                    = "comment"
        val comment                 = Comment(commentIds,productIds,commentCreatedInstant,text)
        val commentResponse         = CommentResponse(commentIds,productIds,text)
        val comments                = flowOf(comment,comment,comment)
        val expected                = listOf(commentResponse,commentResponse,commentResponse)


        coEvery {
            commentRepository.findAll()
        } returns comments

        coEvery {
            commentToResponseMapper.convert(comment)
        } returnsMany expected

        //When
        val actual = commentService.getAll()

        //Then
        assertEquals(expected,actual.toList())
        coVerify(exactly = 3) {
            commentToResponseMapper.convert(any())
        }

    }

    @Test
    fun `when CommentService#getAll is called when database has no records`() = runBlocking {
        //Given
        val expected = emptyList<CommentResponse>()

        coEvery {
            commentRepository.findAll()
        } returns emptyFlow()


        //When
        val actual = commentService.getAll().toList()

        //Then
        assertEquals(expected, actual)
        coVerify {
            commentRepository.findAll()
        }


    }


    @Test
    fun `when CommentService#getById is called with exist id`() = runBlocking {
        //Given
        val commentId               = UUID.randomUUID()
        val productId               = UUID.randomUUID()
        val commentCreatedInstant   = LocalDateTime.now()
        val comment                 = Comment(commentId,productId,commentCreatedInstant,"comment")
        val expected                = CommentResponse(commentId,productId,"comment")

        coEvery {
            commentRepository.findById(commentId)
        } returns comment

        coEvery {
            commentToResponseMapper.convert(comment)
        } returns expected
        //When
        val actual = commentService.getById(commentId)

        //Then
        assertEquals(expected,actual)
        coVerifySequence {
            commentRepository.findById(commentId)
            commentToResponseMapper.convert(comment)
        }

    }

    @Test
    fun `when CommentService#getById is called with not found id`() = runBlocking {
        //Given
        val commentId   = UUID.randomUUID()
        val expected    = null

        coEvery {
            commentRepository.findById(commentId)
        } returns expected


        //When - Then
        assertThrows<CommentNotFoundException> {
            commentService.getById(commentId)
        }

        coVerify(exactly = 1) {
            commentRepository.findById(commentId)
        }

    }


    @Test
    fun `when CommentService#addComment is called with valid request`() = runBlocking {
        //Given
        val commentUUID                 = UUID.randomUUID()
        val productUUID                 = UUID.randomUUID()
        val commentCreationInstant      = LocalDateTime.now()
        val commentRequest              = CommentRequest(productUUID, "Comment")
        val comment                     = Comment(commentUUID,productUUID,commentCreationInstant,"Comment")
        val expected                    = CommentResponse(commentUUID,productUUID,"Comment")

        coEvery {
            commentService.isCommentAlreadyExist(
                    commentRequest.productId!!,
                    commentRequest.text!!
            )
        } returns null

        coEvery {
            requestToCommentMapper.convert(commentRequest)
        } returns comment

        coEvery {
            commentRepository.save(comment)
        } returns comment

        coEvery {
            commentToResponseMapper.convert(comment)
        } returns expected

        //When
        val actual = commentService.addComment(commentRequest)

        //Then
        assertEquals(expected, actual)
        coVerifySequence {
            commentService.isCommentAlreadyExist(commentRequest.productId!!, commentRequest.text!!)
            requestToCommentMapper.convert(commentRequest)
            commentRepository.save(comment)

        }
    }

    @Test
    fun `when CommentService#addComment is called with already exist comment's request`() = runBlocking {
        //Given
        val commentUUID                 = UUID.randomUUID()
        val productUUID                 = UUID.randomUUID()
        val commentCreationInstant      = LocalDateTime.now()
        val commentRequest              = CommentRequest(productUUID, "Comment")
        val expected                    = Comment(commentUUID,productUUID,commentCreationInstant,"Comment")

        coEvery {
            commentService.isCommentAlreadyExist(
                    commentRequest.productId!!,
                    commentRequest.text!!
            )
        } returns expected

        //When - Then
        assertThrows<CommentAlreadyExistException> {
            commentService.addComment(commentRequest)
        }
        coVerify { commentService.isCommentAlreadyExist(commentRequest.productId!!, commentRequest.text!!) }

    }


    @Test
    fun `when CommentService#isCommentAlreadyExist is called with exist arguments`() = runBlocking {
        //Given
        val productId   = UUID.randomUUID()
        val text        = "text"
        val expected    = Comment(UUID.randomUUID(),productId, LocalDateTime.now(),text)

        coEvery {
            commentRepository.isCommentAlreadyExist(productId,text)
        } returns expected

        //When
        val actual = commentService.isCommentAlreadyExist(productId,text)

        //Then
        assertEquals(expected,actual)
    }



    @Test
    fun `when CommentService#isCommentAlreadyExist is called with not exist arguments`() = runBlocking {
        //Given
        val productId   = UUID.randomUUID()
        val text        = "text"
        val expected    = null
        coEvery {
            commentRepository.isCommentAlreadyExist(productId,text)
        } returns expected

        //When
        val actual = commentService.isCommentAlreadyExist(productId,text)

        //Then
        assertEquals(expected,actual)
    }

}