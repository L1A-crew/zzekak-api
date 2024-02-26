package l1a.jjakkak.infra.domain.user.dao

import l1a.jjakkak.core.domain.user.AuthenticationId
import l1a.jjakkak.core.domain.user.UserId
import l1a.jjakkak.infra.domain.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

interface UserEntityDao {
    fun save(entity: UserEntity): UserEntity

    fun findUserByAuthenticationId(authenticationId: AuthenticationId): UserEntity?
    fun findUserByAuthenticationIdAndIsRemoved(authenticationId: AuthenticationId, isRemoved: Boolean): UserEntity?
    fun findUserByUserIdAndIsRemoved(userId: UUID, isRemoved: Boolean): UserEntity?

    fun findAllByIds(ids: Collection<UserId>): List<UserEntity>
}

@Repository
interface UserEntityJpaRepository : JpaRepository<UserEntity, UUID> {
    fun findByAuthenticationEntity_AuthenticationId(authenticationId: String): UserEntity?

    fun findByAuthenticationEntity_AuthenticationIdAndIsRemoved(authenticationId: String, isRemoved: Boolean): UserEntity?
    @Query("select u from UserEntity u where u.userId = :userId and u.isRemoved = :isRemoved")
    fun findByUserEntity_UserIdAndIsRemoved(userId: UUID, isRemoved: Boolean): UserEntity?

    fun findAllByUserIdIn(ids: Collection<UUID>): List<UserEntity>
}

@Repository
internal class UserEntityDaoImpl(
    val delegate: UserEntityJpaRepository
) : UserEntityDao {
    override fun save(entity: UserEntity): UserEntity = delegate.save(entity)

    override fun findUserByAuthenticationId(authenticationId: AuthenticationId): UserEntity? =
        delegate.findByAuthenticationEntity_AuthenticationId(authenticationId.value)

    override fun findUserByAuthenticationIdAndIsRemoved(
        authenticationId: AuthenticationId,
        isRemoved: Boolean
    ): UserEntity? = delegate.findByAuthenticationEntity_AuthenticationIdAndIsRemoved(authenticationId.value, isRemoved)

    override fun findUserByUserIdAndIsRemoved(userId: UUID, isRemoved: Boolean): UserEntity?
     = delegate.findByUserEntity_UserIdAndIsRemoved(userId, isRemoved)



    override fun findAllByIds(ids: Collection<UserId>): List<UserEntity> =
        delegate.findAllByUserIdIn(ids.map { it.value })
}