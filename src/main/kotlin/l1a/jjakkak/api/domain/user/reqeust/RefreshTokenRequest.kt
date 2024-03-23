package l1a.jjakkak.api.domain.user.reqeust

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenRequest(
    @JsonProperty("refreshToken")
    val refreshToken: String
)
