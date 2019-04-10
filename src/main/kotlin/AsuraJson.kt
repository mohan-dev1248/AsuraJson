import lowLevelDataTypes.JSON
import lowLevelDataTypes.JSONArray
import lowLevelDataTypes.JSONObject
import lowLevelDataTypes.JSONValue

class AsuraJson {

    fun toJson(){

    }

    fun fromJson(jsonString: String){

    }

    fun <T : Any> fromJson(json: String,clazz: Class<T>): Any? {

        if (clazz == Int.javaClass){
            val obj = json.toInt()
            return obj
        }

        return null
    }

    fun parse(json: String):JSONValue?{

        return Parser.parse(json)
    }
}