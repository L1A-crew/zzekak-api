package l1a.jjakkak.core.domain.appointment

import l1a.jjakkak.core.domain.address.Address
import l1a.jjakkak.core.domain.common.IdTypeUUID
import l1a.jjakkak.core.domain.user.UserCommand
import l1a.jjakkak.core.domain.user.UserQuery
import java.time.Instant
import java.util.*

@JvmInline
value class AppointmentId(override val value: UUID) : IdTypeUUID

interface AppointmentCommand {
    val id: AppointmentId
    val name: String
    val address: Address
    val appointmentTime: Instant
    val participants: List<UserCommand>

    companion object {
        fun create(
            id: AppointmentId,
            name: String,
            address: Address,
            appointmentTime: Instant,
            participants: List<UserCommand>
        ): AppointmentCommand =
            AppointmentCommandImpl(
                id = id,
                name = name,
                address = address,
                appointmentTime = appointmentTime,
                participants = participants
            )
    }
}

data class AppointmentCommandImpl(
    override val id: AppointmentId,
    override val name: String,
    override val address: Address,
    override val appointmentTime: Instant,
    override val participants: List<UserCommand>
) : AppointmentCommand

interface AppointmentQuery : AppointmentCommand {
    override val id: AppointmentId
    override val name: String
    override val address: Address
    override val appointmentTime: Instant
    override val participants: List<UserQuery>
    val createdAt: Instant
    val updatedAt: Instant

    companion object {
        fun create(
            id: AppointmentId,
            name: String,
            address: Address,
            appointmentTime: Instant,
            participants: List<UserQuery>,
            createdAt: Instant,
            updatedAt: Instant
        ): AppointmentQuery =
            AppointmentQueryImpl(
                id = id,
                name = name,
                address = address,
                appointmentTime = appointmentTime,
                participants = participants,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
    }
}

data class AppointmentQueryImpl(
    override val id: AppointmentId,
    override val name: String,
    override val address: Address,
    override val appointmentTime: Instant,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    override val participants: List<UserQuery>
) : AppointmentQuery