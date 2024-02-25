package l1a.jjakkak.core.domain.user

import l1a.jjakkak.core.domain.common.IdTypeUUID
import java.time.Instant
import java.util.UUID

@JvmInline value class UserId(override val value: UUID): IdTypeUUID

interface UserCommand {
    val id: UserId
    val authentication: AuthenticationCommand

    companion object {
        fun create(
            id: UserId,
            authentication: AuthenticationCommand,
        ): UserCommand =
            UserCommandImpl(
                id = id,
                authentication = authentication
            )
    }
}

internal data class UserCommandImpl(
    override val id: UserId,
    override val authentication: AuthenticationCommand
) : UserCommand

interface UserQuery : UserCommand {
    override val id: UserId
    override val authentication: AuthenticationQuery
    val createdAt: Instant
    val updatedAt: Instant
    var isRemoved: Char

    companion object {
        fun create(
            id: UserId,
            authentication: AuthenticationQuery,
            createdAt: Instant,
            updatedAt: Instant,
            isRemoved: Char
        ): UserQuery =
            UserQueryImpl(
                id = id,
                authentication = authentication,
                createdAt = createdAt,
                updatedAt = updatedAt,
                isRemoved = 'N'
            )
    }
}

internal data class UserQueryImpl(
    override val id: UserId,
    override val authentication: AuthenticationQuery,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    override var isRemoved: Char
) : UserQuery