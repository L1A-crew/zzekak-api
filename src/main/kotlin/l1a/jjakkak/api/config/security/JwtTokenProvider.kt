package l1a.jjakkak.api.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import l1a.jjakkak.core.domain.user.UserId
import l1a.jjakkak.core.domain.user.usecase.JoinOrLoginUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
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
internal class JwtTokenProvider(
    @Value("\${lia.auth.private-key-path}")
    private val privateKey: String,
    @Value("\${lia.auth.public-key-path}")
    private val publicKey: String

) {
    private val alg = Algorithm.RSA256(getPublicKey(), getPrivateKey())

    fun createToken(userId: UUID, now: Instant = Instant.now()): String =
        JWT.create()
            .withSubject(userId.toString())
            .withIssuedAt(now)
            .withExpiresAt(now.plusSeconds(AUTH_VALIDATION_EXPIRY))
            .sign(alg)

    fun validationToken(token: String, now: Instant = Instant.now()): Boolean {
        val jwt = decodeToken(token)

        return now.isBefore(jwt.expiresAt.toInstant())
    }

    fun getUserId(token: String): UUID = UUID.fromString(decodeToken(token).subject)

    fun getAuthentication(token: String): Authentication =
        PreAuthenticatedAuthenticationToken(
           UUID.fromString(decodeToken(token).subject), null, emptyList()
        )

    private fun decodeToken(token: String): DecodedJWT =
        JWT.require(alg)
            .build()
            .verify(token)

    private fun getPrivateKey(): RSAPrivateKey {
        val keyBytes = Files.readAllBytes(Paths.get(privateKey))
        return KeyFactory.getInstance(JoinOrLoginUseCase.ALG)
            .generatePrivate(PKCS8EncodedKeySpec(keyBytes)) as RSAPrivateKey
    }

    private fun getPublicKey(): RSAPublicKey {
        val publicKeyPEM = Files.readString(Paths.get(publicKey))
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")

        val decoded = Base64.getDecoder().decode(publicKeyPEM)
        return KeyFactory.getInstance(JoinOrLoginUseCase.ALG)
            .generatePublic(X509EncodedKeySpec(decoded)) as RSAPublicKey
    }

    companion object {
        const val AUTH_VALIDATION_EXPIRY: Long = 60 * 60 * 24 * 30
    }
}