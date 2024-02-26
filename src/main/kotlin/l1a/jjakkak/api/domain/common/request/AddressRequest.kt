package l1a.jjakkak.api.domain.common.request

import com.fasterxml.jackson.annotation.JsonProperty

internal data class AddressRequest(
    @JsonProperty("cityOrProvince")
    val cityOrProvince: String,
    @JsonProperty("districtOrCity")
    val districtOrCity: String,
    @JsonProperty("postalCode")
    val postalCode: String,
    @JsonProperty("jibunAddress")
    val jibunAddress: String,
    @JsonProperty("roadAddress")
    val roadAddress: String
)
