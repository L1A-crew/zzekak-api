package l1a.jjakkak.api.domain.appointment

import com.fasterxml.jackson.annotation.JsonProperty
import l1a.jjakkak.api.domain.common.request.AddressRequest
import java.time.ZonedDateTime
import java.util.UUID

internal data class CreateAppointmentRequest(
    @JsonProperty("name")
    val name: String = "",
    @JsonProperty("address")
    val address: AddressRequest,
    @JsonProperty("appointmentTime")
    val appointmentTime: ZonedDateTime,
    @JsonProperty("participants")
    val participants: List<UUID>
)
