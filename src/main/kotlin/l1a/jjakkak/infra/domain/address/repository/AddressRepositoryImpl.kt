package l1a.jjakkak.infra.domain.address.repository

import l1a.jjakkak.core.domain.address.Address
import l1a.jjakkak.core.domain.address.repository.AddressRepository

class AddressRepositoryImpl(): AddressRepository {
    /**
     * juso.go.kr 에서 제공하는 API 의 결과 값을 파싱, [Address] 에 담아서 반환한다.
     */
    override fun findAddressByKeyword(keyword: String): List<Address> {
        TODO("Not yet implemented")
    }
}