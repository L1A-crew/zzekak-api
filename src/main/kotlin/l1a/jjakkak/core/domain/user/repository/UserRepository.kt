package l1a.jjakkak.core.domain.user.repository

import l1a.jjakkak.core.domain.user.AuthenticationId
import l1a.jjakkak.core.domain.user.UserCommand
import l1a.jjakkak.core.domain.user.UserId
import l1a.jjakkak.core.domain.user.UserQuery
import java.util.*

interface UserRepository {
    fun save(user: UserCommand): UserQuery

    fun findUserByAuthenticationIdAndIsRemoved(authenticationId: AuthenticationId, isRemoved: Char): UserQuery?

    fun findUserByUserIdAndIsRemoved(userId: UUID, isRemoved: Char): UserQuery?
}