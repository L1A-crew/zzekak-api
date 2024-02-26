package l1a.jjakkak.infra.domain.appointment.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import l1a.jjakkak.infra.domain.address.entity.AddressEntity
import l1a.jjakkak.infra.domain.appointment.entity.AppointmentUserEntity.Companion.TABLE_USER_ADDRESS
import l1a.jjakkak.infra.domain.user.entity.AuthenticationEntity
import l1a.jjakkak.infra.domain.user.entity.UserEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant
import java.util.*

@Entity
@Table(name = TABLE_USER_ADDRESS)
internal class AppointmentUserEntity(
    @EmbeddedId
    val id: AppointmentUserId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("appointmentId")
    @JoinColumn(name = AppointmentEntity.COLUMN_APPOINTMENT_ID)
    val appointment: AppointmentEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = UserEntity.COLUMN_USER_ID)
    val user: UserEntity
) {
    companion object {
        const val TABLE_USER_ADDRESS = "appointment_user"
    }
}

@Embeddable
internal class AppointmentUserId(
    var appointmentId: UUID,
    var userId: UUID
) : Serializable