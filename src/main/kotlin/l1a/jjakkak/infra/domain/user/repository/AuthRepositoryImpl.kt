package l1a.jjakkak.infra.domain.user.repository

import com.fasterxml.jackson.core.type.TypeReference
import l1a.jjakkak.core.domain.user.repository.AuthRepository
import l1a.jjakkak.core.util.ObjectMapper
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Repository
internal class AuthRepositoryImpl(
    @Value("\${lia.auth.social-login-kakao-public-key-url}")
    private val kakaoPublicKeyUrl: String
) : AuthRepository, InitializingBean {
    lateinit var httpClient: HttpClient

    override fun getKakaoLoginPublicKey(): List<AuthRepository.RSAPublicKeyInfo> {
        val request = HttpRequest.newBuilder()
            .uri(URI(kakaoPublicKeyUrl))
            .GET()
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())


        val parsed = ObjectMapper.objectMapper.readValue(
            response.body(),
            KakaoPublicKeys::class.java
        )

        return parsed.keys.map {
            AuthRepository.RSAPublicKeyInfo(
                kid = it.kid,
                kty = it.kty,
                alg = it.alg,
                use = it.use,
                n = it.n,
                e = it.e
            )
        }
    }

    private data class KakaoPublicKeys(
        val keys: List<KeyInfo>
    )

    private data class KeyInfo(
        val kid: String,
        val kty: String,
        val alg: String,
        val use: String,
        val n: String,
        val e: String
    )

    override fun afterPropertiesSet() {
        httpClient = HttpClient.newHttpClient()
    }
}