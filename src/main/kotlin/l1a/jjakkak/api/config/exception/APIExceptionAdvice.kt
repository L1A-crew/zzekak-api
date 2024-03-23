package l1a.jjakkak.api.config.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class APIExceptionAdvice {

    @ExceptionHandler(ZzekakException::class)
    fun handleException(e: ZzekakException): ResponseEntity<ErrorResponse> =
        ErrorResponse.toResponseEntity(e)
}