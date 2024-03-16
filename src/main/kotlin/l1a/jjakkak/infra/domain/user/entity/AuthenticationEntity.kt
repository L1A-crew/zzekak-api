package l1a.jjakkak.infra.domain.user.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import l1a.jjakkak.core.domain.user.AuthenticationType
import l1a.jjakkak.infra.domain.user.converter.AuthenticationTypeConverter
import l1a.jjakkak.infra.domain.user.entity.AuthenticationEntity.Companion.TABLE_AUTHENTICATION
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = TABLE_AUTHENTICATION)
@EntityListeners(AuditingEntityListener::class)
class AuthenticationEntity(
    @Id @Column(name = COLUMN_AUTHENTICATION_ID)
    val authenticationId: String,

    @Column(name = COLUMN_TYPE)
    @Convert(converter = AuthenticationTypeConverter::class)
    var type: AuthenticationType,
) {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = COLUMN_USER_ID)
    var userEntity: UserEntity? = null

    @Column(name = COLUMN_CREATED_AT)
    @CreatedDate
    lateinit var createdAt: Instant

    @Column(name = COLUMN_UPDATED_AT)
    @LastModifiedDate
    lateinit var updatedAt: Instant

    companion object {
        const val TABLE_AUTHENTICATION = "authentication"
        const val COLUMN_AUTHENTICATION_ID = "authentication_id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_TYPE = "type"
        const val COLUMN_CREATED_AT = "created_at"
        const val COLUMN_UPDATED_AT = "updated_at"
    }
}