package l1a.jjakkak.api.domain.user.reqeust

import com.auth0.jwt.JWT
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.readValue
import l1a.jjakkak.api.config.security.JwtTokenProvider
import l1a.jjakkak.core.domain.user.AuthToken
import l1a.jjakkak.core.domain.user.AuthenticationType
import l1a.jjakkak.core.domain.user.message.WithdrawalMessage
import l1a.jjakkak.core.util.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import java.util.*

data class UserWithdrawalRequest (
    @JsonProperty("token")
    val token: String,

) {

    fun toMessage() : WithdrawalMessage =  WithdrawalMessage(
        userId = decodeToken(token),
        isRemoved = 'Y'
    )
    private fun decodeToken(token: String)  = UUID.fromString(JWT.decode(token).subject)
}