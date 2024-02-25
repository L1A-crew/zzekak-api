package l1a.jjakkak.core.domain.user.usecase

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import l1a.jjakkak.core.domain.user.AuthToken
import l1a.jjakkak.core.domain.user.AuthenticationCommand
import l1a.jjakkak.core.domain.user.AuthenticationId
import l1a.jjakkak.core.domain.user.AuthenticationType
import l1a.jjakkak.core.domain.user.Token
import l1a.jjakkak.core.domain.user.UserCommand
import l1a.jjakkak.core.domain.user.UserId
import l1a.jjakkak.core.domain.user.message.JoinOrLoginMessage
import l1a.jjakkak.core.domain.user.repository.UserRepository
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

interface JoinOrLoginUseCase {
    fun joinOrLogin(message: JoinOrLoginMessage): Token
}

@Service
internal class JoinOrLoginUseCaseImpl(
    val userRepo: UserRepository,
    @Value("\${lia.auth.private-key-path}")
    private val privateKey: String,
    @Value("\${lia.auth.public-key-path}")
    private val publicKey: String
) : JoinOrLoginUseCase {
    val algorithm: Algorithm = Algorithm.RSA256(getPublicKey(), getPrivateKey())

    override fun joinOrLogin(message: JoinOrLoginMessage): Token {
        val (token, type) = message
        val decodedToken = type.decode(token).also { it.validate() }
        
        val user = userRepo
            .findUserByAuthenticationIdAndIsRemoved(AuthenticationId(decodedToken.payload.sub), 'N')     //삭제여부 컬럼 추가
            ?: createUser(decodedToken, type).run { userRepo.save(this) }

        val (accessToken, refreshToken) = JWT.create()
            .withIssuer(TOKEN_ISSUER)
            .withSubject(user.id.value.toString())
            .withIssuedAt(Instant.now())
            .run {
                val now = Instant.now()

                withExpiresAt(now.plusSeconds(ACCESS_TOKEN_EXPIRY)).sign(algorithm) to
                        withExpiresAt(now.plusSeconds(REFRESH_TOKEN_EXPIRY)).sign(algorithm)
            }

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

    private fun getPrivateKey(): RSAPrivateKey {
        val keyBytes = Files.readAllBytes(Paths.get(privateKey))
        return KeyFactory.getInstance(ALG)
            .generatePrivate(PKCS8EncodedKeySpec(keyBytes)) as RSAPrivateKey
    }

    private fun getPublicKey(): RSAPublicKey {
        val publicKeyPEM = Files.readString(Paths.get(publicKey))
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")

        val decoded = Base64.getDecoder().decode(publicKeyPEM)
        return KeyFactory.getInstance(ALG)
            .generatePublic(X509EncodedKeySpec(decoded)) as RSAPublicKey
    }

    companion object {
        const val TOKEN_ISSUER = "liaServer"

        const val ACCESS_TOKEN_EXPIRY = 60 * 60L
        const val REFRESH_TOKEN_EXPIRY = 60 * 60 * 24 * 30 * 6L

        const val ALG = "RSA"
    }
}