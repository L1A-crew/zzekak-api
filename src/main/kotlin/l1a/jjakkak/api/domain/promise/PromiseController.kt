package l1a.jjakkak.api.domain.promise

import l1a.jjakkak.api.ApiUrl
import l1a.jjakkak.api.domain.user.reqeust.UserCreateRequest
import l1a.jjakkak.api.domain.user.response.UserCreateResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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