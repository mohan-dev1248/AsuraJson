package lowLevelDataTypes

import java.math.BigInteger

class JSONObject : JSONValue, JSON{
    private var content = mutableMapOf<String,Any>()

    fun add(key: String, value: Any): Unit{
        content.put(key,value)
    }

    var length: Int = 0
        get() = content.size

    /*fun getAt(index: Int) : MutableMap.MutableEntry<String, Any> {
        var entry :MutableMap.MutableEntry<String, Any>
        return entry
    }*/

    fun int(key: String) : Int?{
        return content.get(key).toString().toInt()
    }

    fun long(key: String): Long?{
        return content.get(key).toString().toLong()
    }

    fun bigInteger(key:String): BigInteger?{
        return content.get(key).toString().toBigInteger()
    }

    fun string(key:String) : String?{
        return content.get(key).toString()
    }

    fun double(key:String) : Double{
        return content.get(key).toString().toDouble()
    }

    fun boolean(key: String): Boolean?{
        return content.get(key).toString().toBoolean()
    }

    fun obj(key:String): JSONObject?{
        return null
    }

    fun array(key: String): JSONArray?{
        return null
    }
}