import lowLevelDataTypes.*

class AsuraJson {

    fun toJson(clas: Any) {

    }

    fun fromJson(jsonString: String) {

    }

    fun <T : Any> fromJson(json: String, clazz: Class<T>): Any? {

        if (clazz == Int.javaClass) {
            val obj = json.toInt()
            return obj
        }

        return null
    }

    fun parse(json: String): JSONValue? {
        return Parser.parseJson(json)?.first
    }

    fun serialize(json: JSONValue): String {
        when (json) {
            is JSONNull -> return "null"
            is JSONBoolean -> return json.bool.toString()
            is JSONDouble -> return json.value.toString()
            is JSONString -> return "\"${json.string}\""
            is JSONArray -> {
                var string = "["
                if (json.length > 0) {
                    string += serialize(json.getAt(0)!!)
                    for (i in 1..json.length - 1) {
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
                    for (i in 0..kvps.size - 1) {
                        string += "\"${kvps.get(i).first}\"" + ":"
                        string += serialize(kvps.get(i).second)
                    }
                }
                string += "}"
                return string
            }
            else -> return "Halikenstien"
        }
    }
}