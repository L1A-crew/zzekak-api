package l1a.jjakkak.api.domain.user.reqeust

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.readValue
import l1a.jjakkak.core.domain.user.AuthToken
import l1a.jjakkak.core.domain.user.AuthenticationType
import l1a.jjakkak.core.domain.user.message.WithdrawalMessage
import l1a.jjakkak.core.util.ObjectMapper
import java.util.*

data class UserWithdrawalRequest (
    @JsonProperty("token")
    val token: String
) {


    fun toMessage() : WithdrawalMessage =  WithdrawalMessage(
        decodeToken = decodeToken(token),
        isRemoved = 'Y'
    )
    private fun decodeToken(token: String) : AuthToken {
        val (header, payload, sig) =
            token.split(AuthenticationType.KAKAO_ID_TOKEN_DELIMITER)
                .map { Base64.getUrlDecoder().decode(it) }
                .map { String(it) }


        val decodedHeader = ObjectMapper.objectMapper.readValue<AuthToken.Header>(header)
        val decodedPayload = ObjectMapper.objectMapper.readValue<AuthToken.Payload>(payload)

        return AuthToken(
            header = decodedHeader,
            payload = decodedPayload,
            signature = sig
        )
    }
}