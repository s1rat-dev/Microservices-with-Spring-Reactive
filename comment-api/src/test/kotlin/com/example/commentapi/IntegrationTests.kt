package com.example.commentapi

import com.example.commentapi.commentmanager.Comment
import com.example.commentapi.commentmanager.CommentRepository
import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.LocalDateTime
import java.util.*


fun postgres(imageName: String, opts: JdbcDatabaseContainer<Nothing>.() -> Unit ) =
        PostgreSQLContainer<Nothing>(DockerImageName.parse(imageName)).apply(opts)

@DataR2dbcTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Testcontainers
class IntegrationTests {

    private val productId = UUID.randomUUID()
    private val text = "text"
    private val comment: Comment = Comment(null, productId, LocalDateTime.now(),text)


    @Container
    val container = postgres("postgres:13-alpine") {
        withDatabaseName("db")
        withUsername("user")
        withPassword("password")
    }


    @Autowired
    private lateinit var commentRepository: CommentRepository


    @TestConfiguration
    internal class TestConfig {
        @Bean
        fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
            val initializer = ConnectionFactoryInitializer()
            initializer.setConnectionFactory(connectionFactory)
            val populator = CompositeDatabasePopulator()
            populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("test/init.sql")))
            initializer.setDatabasePopulator(populator)
            return initializer
        }
    }

    @BeforeEach
    fun insertData() : Unit = runBlocking {
        commentRepository.save(comment)

    }

    @AfterEach
    fun deleteAll() = runBlocking {
        commentRepository.deleteAll()
    }

    @Test
    fun `get all records from db`() = runBlocking {
        commentRepository.findAll().collect(){
            println(it)
        }
    }

    @Test
    fun `add record to db`() = runBlocking{
        commentRepository.save(comment)
        commentRepository.findAll().collect(){
            println(it)
        }
    }

    @Test
    fun `get record that belong to given id from db`() = runBlocking{
        val commentId : UUID = commentRepository.findAll().map { it.id }.first()!!
        val actual = commentRepository.findById(commentId)

        println("$actual call with id $commentId")
    }

    @Test
    fun `get record that belong to given text and productId from db`() = runBlocking{
        val actual = commentRepository.isCommentAlreadyExist(productId,text)

        println("$actual call with given $productId and $text")
    }



}