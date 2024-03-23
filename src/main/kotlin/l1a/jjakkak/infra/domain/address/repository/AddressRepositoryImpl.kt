package l1a.jjakkak.infra.domain.address.repository

import l1a.jjakkak.core.domain.address.Address
import l1a.jjakkak.core.domain.address.Coordinate
import l1a.jjakkak.core.domain.address.repository.AddressRepository
import l1a.jjakkak.infra.domain.address.model.AddressObject
import l1a.jjakkak.infra.domain.address.model.AddressResponse
import l1a.jjakkak.infra.domain.address.model.CoordinateResponse
import org.springframework.beans.factory.annotation.Value

class AddressRepositoryImpl(
    @Value("\${lia.api-key.addr}")  //주소검색 apikey
    val aConfmKey: String,
    @Value("\${lia.api-key.coord}")
    val cConfmKey: String
): AddressRepository {
    /**
     * juso.go.kr 에서 제공하는 API 의 결과 값을 파싱, [Address] 에 담아서 반환한다.
     */
    override fun findAddressByKeyword(keyword: String): List<Address>? {
        val addrResponse: List<Address> = ConnectionModule(aConfmKey).searchAddr(keyword)
        return addrResponse
    }

    override fun findCoordinateByAddressInfo(addrObj: AddressObject): Coordinate {
        val coordinateResponse: Coordinate = ConnectionModule(cConfmKey).searchCoordinate(addrObj)
        return coordinateResponse
    }
}