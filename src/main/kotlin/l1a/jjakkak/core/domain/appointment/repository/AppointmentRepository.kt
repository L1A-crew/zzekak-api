package l1a.jjakkak.core.domain.appointment.repository

import l1a.jjakkak.core.domain.appointment.AppointmentCommand
import l1a.jjakkak.core.domain.appointment.AppointmentQuery

interface AppointmentRepository {
    fun save(appointmentCommand: AppointmentCommand): AppointmentQuery
}