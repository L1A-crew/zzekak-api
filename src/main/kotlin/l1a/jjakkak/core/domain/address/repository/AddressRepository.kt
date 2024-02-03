package l1a.jjakkak.core.domain.address.repository

import l1a.jjakkak.core.domain.address.Address

interface AddressRepository {
    fun findAddressByKeyword(keyword: String): List<Address>
}