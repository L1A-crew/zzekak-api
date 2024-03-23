package l1a.jjakkak.core.domain.user.usecase

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import l1a.jjakkak.core.domain.user.AuthToken
import l1a.jjakkak.core.domain.user.AuthenticationCommand
import l1a.jjakkak.core.domain.user.AuthenticationId
import l1a.jjakkak.core.domain.user.AuthenticationType
import l1a.jjakkak.core.domain.user.Token
import l1a.jjakkak.core.domain.user.UserCommand
import l1a.jjakkak.core.domain.user.UserId
import l1a.jjakkak.core.domain.user.message.JoinOrLoginMessage
import l1a.jjakkak.core.domain.user.repository.AuthRepository
import l1a.jjakkak.core.domain.user.repository.UserRepository
import l1a.jjakkak.core.domain.user.usecase.common.JwtMixin
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.time.Instant
import java.util.*

@Service
internal class JoinOrLoginUseCase(
    val userRepo: UserRepository,
    val authRepo: AuthRepository,
    @Value("\${lia.auth.private-key-path}")
    private val privateKey: String,
    @Value("\${lia.auth.public-key-path}")
    private val publicKey: String,
    @Value("\${lia.auth.social-login-app-key}")
    private val appKey: String
) : JwtMixin, InitializingBean {
    lateinit var algorithm: Algorithm

    override fun afterPropertiesSet() {
        algorithm = Algorithm.RSA256(getPublicKey(publicKey), getPrivateKey(privateKey))
    }

    fun joinOrLogin(message: JoinOrLoginMessage): Token {
        val (token, type) = message

        val decodedToken = type.decode(token)

        val selectedPublicKeyInfo =
            authRepo.getKakaoLoginPublicKey().find { it.kid == decodedToken.header.kid }
                ?: throw JWTVerificationException("Not Found Kakao PublicKeyInfo")

        type.validate(
            token = token,
            appKey = appKey,
            rsaPublicKeyInfo = selectedPublicKeyInfo
        )

        val user = userRepo
            .findUserByAuthenticationIdAndIsRemoved(AuthenticationId(decodedToken.payload.sub), false)     //삭제여부 컬럼 추가
            ?: createUser(decodedToken, type).run { userRepo.save(this) }

        val (accessToken, refreshToken) = createToken(user.id.value, algorithm)

        return Token(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    private fun createUser(authToken: AuthToken, type: AuthenticationType) =
        UserCommand.create(
            id = UserId(UUID.randomUUID()),
            authentication = AuthenticationCommand.create(
                id = AuthenticationId(authToken.payload.sub),
                type = type
            )
        )
}