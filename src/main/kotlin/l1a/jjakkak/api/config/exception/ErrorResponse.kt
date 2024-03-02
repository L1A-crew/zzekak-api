package l1a.jjakkak.api.config.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ErrorResponse(
    val code: String,
    var message: String
) {
    companion object{
        fun toResponseEntity(e: ZzekakException): ResponseEntity<ErrorResponse> =
            ResponseEntity.status(e.ex.rStatus.value())
                .body(ErrorResponse(e.ex.code, e.ex.message))
    }

}