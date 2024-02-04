package l1a.jjakkak.infra.domain.address.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class AddressRequest (
    val keyword: String,
    val confmKey: String,
    val currentPage: Int,
    val countPerPage: Int,
    val resultType: String,
    val hstryYn: String,
    val firstSort: String,
    val addInfoYn: String
){
    constructor(keyword:String, confmKey: String, ): this(keyword, confmKey,
        1, 20, "json", "N", "none", "N")

    fun returnToJSON(): JsonObject{
        return Json.encodeToJsonElement(this).jsonObject
    }

}