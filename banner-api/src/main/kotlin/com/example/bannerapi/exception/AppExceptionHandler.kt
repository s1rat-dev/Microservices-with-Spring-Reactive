package com.example.bannerapi.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.support.WebExchangeBindException
import java.time.LocalDateTime

@ControllerAdvice
class AppExceptionHandler {

    @ExceptionHandler(BannerAlreadyExistException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBannerAlreadyExistException(ex : BannerAlreadyExistException) : ResponseEntity<Any?> =
            ResponseEntity(generateError(ex.message,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST)


    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleValidationExceptions(ex: WebExchangeBindException): ResponseEntity<Any?> {

        val errors: MutableList<ApiError> = ex.bindingResult.allErrors.map {
            generateError(it.defaultMessage,HttpStatus.BAD_REQUEST)
        }.toMutableList()

        return ResponseEntity.badRequest().body(mutableMapOf(Pair("errors", errors)))
    }


    @ExceptionHandler(IdNotExistException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleIdNotFoundException(ex: IdNotExistException) : ResponseEntity<Any?> =
            ResponseEntity(generateError(ex.message,HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND)




    private fun generateError(message: String?, status: HttpStatus) : ApiError {
        return ApiError(
                message = message,
                status = status,
                timestamp = LocalDateTime.now()
        )
    }

}


