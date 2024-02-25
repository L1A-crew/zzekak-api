package l1a.jjakkak.api.domain.user.response

import com.fasterxml.jackson.annotation.JsonProperty
import l1a.jjakkak.core.domain.user.Token
import l1a.jjakkak.core.domain.user.WithdrawalResult

class WithdrawalResponse (
    @JsonProperty("result")
    val quitResult: Char,
    @JsonProperty("message")
    val message: String
){
    companion object {
        fun from(result: WithdrawalResult) = WithdrawalResponse(
            quitResult = result.result,
            message = result.message
        )
    }
}