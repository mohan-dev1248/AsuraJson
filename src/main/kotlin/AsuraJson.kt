import lowLevelDataTypes.*

class AsuraJson {

    fun toJson(clas: Any) {

    }

    fun <T> fromJson(json: String): T? {
        when (val jsonValue: JSONValue? = parse(json)) {
            is JSONNull -> return null
            is JSONBoolean -> {
                try {
                    return jsonValue.bool as T
                } catch (e: Exception) {
                    println("Unable to get you the required type")
                }
            }
            is JSONInt -> {
                try {
                    return jsonValue.data as T
                } catch (e: Exception) {
                    println("Unable to get you the required type")
                }
            }
            is JSONLong -> return jsonValue.value as T
            is JSONDouble -> return jsonValue.value as T
            is JSONBigInteger -> return jsonValue.bigInteger as T
            is JSONBigDecimal -> return jsonValue.bigDecimal as T
            is JSONString -> return "\"${jsonValue.string}\"" as T
            is JSONArray -> {

            }
            is JSONObject -> {

            }
            else -> return null
        }

        return null
    }

    fun parse(json: String): JSONValue? {
        return Parser.parse(json)
    }

    fun serialize(json: JSONValue): String {
        when (json) {
            is JSONNull -> return "null"
            is JSONBoolean -> return json.bool.toString()
            is JSONInt -> return json.data.toString()
            is JSONLong -> return json.value.toString()
            is JSONDouble -> return json.value.toString()
            is JSONBigInteger -> return json.bigInteger.toString()
            is JSONBigDecimal -> return json.bigDecimal.toString()
            is JSONString -> return "\"${json.string}\""
            is JSONArray -> {
                var string = "["
                if (json.length > 0) {
                    string += serialize(json.getAt(0)!!)
                    for (i in 1 until json.length) {
                        string += "," + serialize(json.getAt(i)!!)
                    }
                }
                string += "]"
                return string
            }
            is JSONObject -> {
                var string = "{"
                if (json.length > 0) {
                    val kvps = json.getListofKeyValuePairs()
                    string += "\"${kvps[0].first}\"" + ":"
                    string += serialize(kvps[0].second)
                    for (i in 1 until kvps.size) {
                        string += "," + "\"${kvps[i].first}\"" + ":"
                        string += serialize(kvps[i].second)
                    }
                }
                string += "}"
                return string
            }
            else -> return "Halikenstien"
        }
    }
}