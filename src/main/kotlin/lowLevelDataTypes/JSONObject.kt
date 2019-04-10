package lowLevelDataTypes

import java.math.BigInteger

class JSONObject : JSONValue {
    private var content = mutableMapOf<String, JSONValue>()

    fun add(key: String, value: JSONValue): Unit {
        content.put(key, value)
    }

    var length: Int = 0
        get() = content.size


    fun get(key: String): JSONValue? {
        return content.get(key)
    }

    fun getListofKeyValuePairs(): List<Pair<String, JSONValue>> {
        return content.toList()
    }


//TODO - Need to implement these when providing ObjectBinging APIs
//    fun int(key: String) : Int?{
//        return content.get(key).toString().toInt()
//    }
//
//    fun long(key: String): Long?{
//        return content.get(key).toString().toLong()
//    }
//
//    fun bigInteger(key:String): BigInteger?{
//        return content.get(key).toString().toBigInteger()
//    }
//
//    fun string(key:String) : String?{
//        return content.get(key).toString()
//    }
//
//    fun double(key:String) : Double{
//        return content.get(key).toString().toDouble()
//    }
//
//    fun boolean(key: String): Boolean?{
//        return (content.get(key) as JSONBoolean).bool
//    }
//
//    fun obj(key:String): JSONObject?{
//        return content.get(key) as JSONObject
//    }
//
//    fun array(key: String): JSONArray?{
//        return content.get(key) as JSONArray
//    }
}