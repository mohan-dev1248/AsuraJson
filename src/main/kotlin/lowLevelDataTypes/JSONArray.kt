package lowLevelDataTypes

import lowLevelDataTypes.JSONValue

class JSONArray :JSONValue, JSON{
    private var content = mutableListOf<Any>()

    fun add(item: Any){
        content.add(item)
    }

    var length: Int = 0
        get() = content.size

    fun getAt(index: Int) : Any?{
        return content.get(index)
    }

}