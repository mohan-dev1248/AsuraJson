import lowLevelDataTypes.*

object Parser {

//    lateinit var bracesStack: MutableList<String>
//    var parsedLength: Int = 0
//
//    var openingExpectedVector = mutableListOf<Char>('{', '[')
//    var keyExpectingVector = mutableListOf<Char>('\"', '}')
//    var afterKeyExpectingVector = mutableListOf<Char>(':')
//    var afterElementExpectingVector = mutableListOf<Char>(',', '}', ']')
//    var elementExpectingVector = mutableListOf<Char>()
//
//    var currentExpecting = openingExpectedVector
//
//    private fun init() {
//        bracesStack = mutableListOf()
//        parsedLength = 0
//    }

    fun parse(json: String): JSONValue? {
//        init()
//
//        val length = json.length
//        var rootObject: JSONValue? = null
//
//        if (json[0] == '{') {
//            rootObject = JSONObject()
//            bracesStack.add("{")
//            currentExpecting = keyExpectingVector
//        } else if (json[0] == '[') {
//            rootObject = JSONArray()
//            bracesStack.add("[")
//            currentExpecting = elementExpectingVector
//        } else if (json == "null") {
//            rootObject = JSONNull()
//        } else {
//            return null
//        }
//        parsedLength++
//
//        while (parsedLength < length) {
//            var key = ""
//            if (currentExpecting == keyExpectingVector) {
//
//                if (currentExpecting.contains(json[parsedLength])) {
//                    if (json[parsedLength] == '\"') {
//                        parsedLength++
//                        while (json[parsedLength] != '\"') {
//                            key += json[parsedLength++].toString()
//                        }
//                        currentExpecting = afterKeyExpectingVector
//                    }
//                } else {
//                    return null
//                }
//            } else if (currentExpecting == afterKeyExpectingVector) {
//
//            }
//
//
//            if (json[parsedLength] == ']') {
//                if (bracesStack.size > 0 && bracesStack.get(bracesStack.size - 1) == "[") {
//                    bracesStack.removeAt(bracesStack.size - 1)
//                } else {
//                    return null
//                }
//            }
//
//            if (json[parsedLength] == '}') {
//                if (bracesStack.size > 0 && bracesStack.get(bracesStack.size - 1) == "{") {
//                    bracesStack.removeAt(bracesStack.size - 1)
//                } else {
//                    return null
//                }
//            }
//            if (json[parsedLength] == '{') {
//                bracesStack.add("{")
//            } else if (json[parsedLength] == '[') {
//                bracesStack.add("[")
//            }
//
//            parsedLength++
//        }
//
//        if (bracesStack.size > 0)
//            return null
//
//        return rootObject
        return null
    }


    fun nullParse(json: String): Pair<JSONNull, String>? {
        if (json.length > 3 && json.subSequence(0, 4) == "null") {
            return Pair(JSONNull(), json.subSequence(4, json.length).toString())
        }
        return null
    }

    fun booleanParse(json: String): Pair<JSONBoolean, String>? {
        if (json.length > 3 && json.subSequence(0, 4) == "true") {
            return Pair(JSONBoolean(true), json.subSequence(4, json.length).toString())
        }
        if (json.length > 4 && json.subSequence(0, 5) == "false") {
            return Pair(JSONBoolean(false), json.subSequence(5, json.length).toString())
        }
        return null
    }

    fun numberParse(json: String): Pair<JSONDouble, String>? {
        val expression = "-?([1-9][0-9]*((.[0-9]+)?([eE][+-]?[0-9]+)?)?)|(0(.[0-9]+)([eE][+-]?[0-9]+)?)".toRegex()
        val obj = expression.find(json, 0)?.value
        if (obj != null && obj.toString()==json.subSequence(0,obj.length)) {
            return Pair(JSONDouble(obj.toString().toDouble()), expression.split(json, 2).get(1))
        }
        return null
    }

    fun stringParse(json: String): Pair<JSONString, String>? {
        if (json.length > 0 && json[0] == '\"') {
            var string: String = ""
            var parsedLength = 1

            while (parsedLength < json.length && json[parsedLength] != '\"') {
                val subString = json.subSequence(parsedLength, parsedLength + 2)

                if (subString == "\\\b" || subString == "\\\n" || subString == "\\\r" || subString == "\\\t" ||
                    subString == "\\\""
                ) {
                    string += json[parsedLength + 1].toString()
                    parsedLength += 2
                    continue
                }

                string += json[parsedLength++].toString()
            }
            return Pair(JSONString(string), json.subSequence(parsedLength + 1, json.length).toString())
        }
        return null
    }

    fun arrayParse(json: String): Pair<JSONArray, String>? {
        if (json[0] == '[') {
            var jsonArray = JSONArray()
            var currentString = json.subSequence(1, json.length).toString()
            do {
                var pair = parseJSONValue(currentString.trimStart())
                if (pair == null) {
                    if (currentString.trimStart()[0] == ']') {
                        return Pair(jsonArray, currentString.subSequence(1, currentString.length).toString())
                    }
                    return null
                }
                jsonArray.add(pair!!.first)
                val hasComma = pair!!.second.trimStart().startsWith(",")
                if (hasComma) {
                    currentString = pair!!.second.trimStart().subSequence(1, pair!!.second.trimStart().length).toString()
                } else {
                    currentString = pair!!.second.trimStart()
                }
            } while (currentString.length > 0);
        }
        return null
    }

    fun objectParse(json: String): Pair<JSONObject, String>? {
        if (json[0] == '{') {
            var jsonObject = JSONObject()
            var currentString = json.subSequence(1,json.length).toString()
            do{

            }while (currentString.length>0)
        }
        return null
    }

    fun parseJSONValue(json: String): Pair<JSONValue, String>? {
        var pair: Pair<JSONValue, String>? = Pair(JSONNull(), "")

        pair = nullParse(json)
        if (pair != null) {
            //println("Found an null as value ${json[0]}")
            return pair
        }

        pair = booleanParse(json)
        if (pair != null) {
            //println("Found an boolean as value ${json[0]}")
            return pair
        }

        pair = numberParse(json)
        if (pair != null) {
            //println("Found an number as value ${json[0]}")
            return pair
        }

        pair = stringParse(json)
        if (pair != null) {
            //println("Found an string as value ${json[0]}")
            return pair
        }

        pair = arrayParse(json)
        if (pair != null) {
            //println("Found an array as value ${json[0]}")
            return pair
        }


        return null
    }

}