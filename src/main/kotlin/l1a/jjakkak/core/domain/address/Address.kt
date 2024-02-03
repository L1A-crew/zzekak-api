package l1a.jjakkak.core.domain.address

interface Address {
    /** 시, 도 */
    val cityOrProvince: String
    /** 구, 시 */
    val districtOrCity: String
    /** 우편번호 */
    val postalCode: String
    /** 지번주소 */
    val jibunAddress: String
    /** 도로명주소 */
    val roadAddress: String
}