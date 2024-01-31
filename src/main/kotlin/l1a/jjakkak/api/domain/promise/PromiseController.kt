package l1a.jjakkak.api.domain.promise

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(
    produces = [MediaType.APPLICATION_JSON_VALUE],
    consumes = [MediaType.APPLICATION_JSON_VALUE]
)
interface PromiseController {
    @PostMapping("/promise/test") 
    fun test(): String
}
class PromiseControllerImpl :  PromiseController {
    override fun test(): String {
        return "hello"
    }

}