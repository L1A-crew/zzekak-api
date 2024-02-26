package l1a.jjakkak.core.domain.user

import l1a.jjakkak.core.domain.common.IdTypeUUID
import java.time.Instant
import java.util.UUID

@JvmInline value class UserId(override val value: UUID): IdTypeUUID

interface UserCommand {
    val id: UserId
    val authentication: AuthenticationCommand
    val isRemoved: Boolean

    companion object {
        fun create(
            id: UserId,
            authentication: AuthenticationCommand,
            isRemoved: Boolean = false
        ): UserCommand =
            UserCommandImpl(
                id = id,
                authentication = authentication,
                isRemoved = isRemoved
            )
    }
}

internal data class UserCommandImpl(
    override val id: UserId,
    override val authentication: AuthenticationCommand,
    override val isRemoved: Boolean
) : UserCommand

interface UserQuery : UserCommand {
    override val id: UserId
    override val authentication: AuthenticationQuery
    val createdAt: Instant
    val updatedAt: Instant

    fun deleteUser(): UserQuery =

        with(this) {
             create(
                 id = id,
                 authentication = authentication,
                 createdAt = createdAt,
                 updatedAt = updatedAt,
                 isRemoved = true
            )
        }

    companion object {
        fun create(
            id: UserId,
            authentication: AuthenticationQuery,
            createdAt: Instant,
            updatedAt: Instant,
            isRemoved: Boolean = false
        ): UserQuery =
            UserQueryImpl(
                id = id,
                authentication = authentication,
                createdAt = createdAt,
                updatedAt = updatedAt,
                isRemoved = isRemoved
            )
    }
}

internal data class UserQueryImpl(
    override val id: UserId,
    override val authentication: AuthenticationQuery,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    override val isRemoved: Boolean
) : UserQuery