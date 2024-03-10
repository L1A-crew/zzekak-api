package l1a.jjakkak.core.domain.user

import com.fasterxml.jackson.module.kotlin.readValue
import l1a.jjakkak.core.util.ObjectMapper
import java.util.*

enum class AuthenticationType(val code: String) {
    KAKAO(code = "ka") {
        override fun decode(token: String): AuthToken {
            val (header, payload, sig) =
                token.split(KAKAO_ID_TOKEN_DELIMITER)
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
    },
    APPLE(code = "ap") {
        override fun decode(token: String): AuthToken {
            TODO()
        }
    };



    abstract fun decode(token: String): AuthToken

    companion object {
        const val KAKAO_ID_TOKEN_DELIMITER = '.'

        fun from(code: String): AuthenticationType =
            entries.firstOrNull { it.code == code }
                ?: throw IllegalArgumentException("$code is illegal Argument [AuthenticationType]")
    }
}