package l1a.jjakkak.infra.domain.address.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

data class CoordinateRequest(
    val confmKey: String,       //API KEY
    val admCd: String,          //행정구역코드
    val rnMgtSn: String,        //도로명 코드
    val udrtYn: String,         //지하 여부. 0: 지상, 1: 지하
    val buldMnnm: String,       //건물 본번
    val buldSlno: String,       //건물 부번
    val resultType: String      //리턴 형식
) {
    constructor(confmKey: String, admCd: String, rnMgtSn: String, udrtYn: String, buldMnnm: String, buldSlno: String)
    : this(confmKey, admCd,rnMgtSn, udrtYn, buldMnnm, buldSlno, "json"){}

    fun returnToJSONString(): JsonObject {
        return Json.encodeToJsonElement(this).jsonObject
    }
}