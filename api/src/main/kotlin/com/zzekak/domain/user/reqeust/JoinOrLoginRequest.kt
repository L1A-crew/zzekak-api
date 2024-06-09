package com.zzekak.domain.user.reqeust

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.zzekak.domain.user.AuthenticationType
import com.zzekak.domain.user.dto.AuthenticationTypeDto
import com.zzekak.domain.user.message.JoinOrLoginMessage

@JsonDeserialize
internal data class JoinOrLoginRequest(
    @JsonProperty("token")
    val token: String,
    @JsonProperty("authenticationType")
    val authenticationType: AuthenticationTypeDto
) {
    fun toMessage(): JoinOrLoginMessage =
        JoinOrLoginMessage(
            token = token,
            type = AuthenticationType.from(authenticationType.code),
        )
}
