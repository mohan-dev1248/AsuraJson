package lowLevelDataTypes

import lowLevelDataTypes.JSONValue

class JSONArray :JSONValue, JSON{
    private var content = mutableListOf<JSONValue>()

    fun add(item: JSONValue){
        content.add(item)
    }

    var length: Int = 0
        get() = content.size

    fun getAt(index: Int) : JSONValue?{
        return content.get(index)
    }

}