package l1a.jjakkak.api.domain.user

import l1a.jjakkak.api.ApiUrl
import l1a.jjakkak.api.domain.user.reqeust.JoinOrLoginRequest
import l1a.jjakkak.api.domain.user.reqeust.UserWithdrawalRequest
import l1a.jjakkak.api.domain.user.response.WithdrawalResponse
import l1a.jjakkak.core.domain.user.usecase.WithdrawalUseCase
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping(
    produces = [MediaType.APPLICATION_JSON_VALUE],
    consumes = [MediaType.APPLICATION_JSON_VALUE]
)
internal interface UserWithdrawalController {
    @PostMapping(ApiUrl.USER_WITHDRAWAL)
    fun userWithdrawal(@RequestBody request: UserWithdrawalRequest): WithdrawalResponse



}
@RestController
internal class UserWithdrawalControllerImpl (
    val useCase: WithdrawalUseCase
): UserWithdrawalController  {
    override fun userWithdrawal(request: UserWithdrawalRequest): WithdrawalResponse =
        useCase.widthdrawal(request.toMessage()).run { WithdrawalResponse.from(this) }

}