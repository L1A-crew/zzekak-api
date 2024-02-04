package l1a.jjakkak

import l1a.jjakkak.core.domain.address.repository.AddressRepository
import l1a.jjakkak.infra.domain.address.repository.AddressRepositoryImpl
import l1a.jjakkak.infra.domain.address.repository.ConnectionModule
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.reflect.Constructor

@SpringBootTest
class AddressTest (
) {

    @Test
    fun requestTest() {
        val keyword: String = "상암동 월드컵파크"
        var result = ConnectionModule("").searchAddr(keyword)
        println(result)
    }
}