import lowLevelDataTypes.*

object Parser {
    private fun parseNull(json: String): Pair<JSONNull, String>? {
        if (json.length > 3 && json.subSequence(0, 4) == "null") {
            return Pair(JSONNull(), json.subSequence(4, json.length).toString())
        }
        return null
    }

    private fun parseBoolean(json: String): Pair<JSONBoolean, String>? {
        if (json.length > 3 && json.subSequence(0, 4) == "true") {
            return Pair(JSONBoolean(true), json.subSequence(4, json.length).toString())
        }
        if (json.length > 4 && json.subSequence(0, 5) == "false") {
            return Pair(JSONBoolean(false), json.subSequence(5, json.length).toString())
        }
        return null
    }

    private fun parseNumber(json: String): Pair<JSONValue, String>? {
        val expression =
            "-?(0|[1-9][0-9]*)((.[0-9]+)?([eE][+-]?[0-9]+)?)?".toRegex()//|(0(.[0-9]+)([eE][+-]?[0-9]+)?)".toRegex()
        val obj = expression.find(json, 0)?.value
        if (obj != null && obj.toString() == json.subSequence(0, obj.length)) {
            return Pair(getNumberWithDataType(obj.toString())!!, expression.split(json, 2).get(1))
        }
        return null
    }

    private fun getNumberWithDataType(number: String): JSONValue?{
        try{
            val obj = number.toInt()
            return JSONInt(obj)
        }catch (e : NumberFormatException){
            //The number is not an integer
        }
        try{
            val obj = number.toLong()
            return  JSONLong(obj)
        }catch (e: NumberFormatException){
            //The number is not a long
        }
        try{
            val obj = number.toBigInteger()
            return  JSONBigInteger(obj)
        }catch (e: NumberFormatException){
            //The number is not a BigInteger
        }
        try {
            val obj = number.toDouble()
            return JSONDouble(obj)
        }catch (e: NumberFormatException){
            //The number is not a Double value
        }
        try{
            val obj = number.toBigDecimal()
            return JSONBigDecimal(obj)
        }catch (e: NumberFormatException){
            //The number is not a BigDecimal
        }

        return null
    }

    private fun parseString(json: String): Pair<JSONString, String>? {
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

    private fun parseArray(json: String): Pair<JSONArray, String>? {
        if (json[0] == '[') {
            var jsonArray = JSONArray()
            var currentString = json.subSequence(1, json.length).toString()
            do {
                var pair = parseJson(currentString.trimStart())
                if (pair == null) {
                    if (currentString.trimStart()[0] == ']') {
                        return Pair(jsonArray, currentString.subSequence(1, currentString.length).toString())
                    }
                    return null
                }
                jsonArray.add(pair!!.first)
                val hasComma = pair!!.second.trimStart().startsWith(",")
                if (hasComma) {
                    currentString =
                        pair!!.second.trimStart().subSequence(1, pair!!.second.trimStart().length).toString()
                } else {
                    currentString = pair!!.second.trimStart()
                }
            } while (currentString.length > 0);
        }
        return null
    }

    private fun parseObject(json: String): Pair<JSONObject, String>? {
        if (json[0] == '{') {
            var jsonObject = JSONObject()
            var currentString = json.subSequence(1, json.length).toString()
            do {
                var pair = parseKeyValue(currentString.trimStart())
                if (pair == null) {
                    if (currentString.trimStart()[0] == '}') {
                        return Pair(jsonObject, currentString.subSequence(1, currentString.length).toString())
                    }
                    return null
                }
                jsonObject.add(pair.first.first, pair.first.second)
                val hasComma = pair!!.second.trimStart().startsWith(",")
                if (hasComma) {
                    currentString =
                        pair!!.second.trimStart().subSequence(1, pair!!.second.trimStart().length).toString()
                } else {
                    currentString = pair!!.second.trimStart()
                }
            } while (currentString.length > 0)
        }
        return null
    }

    private fun parseKeyValue(json: String): Pair<Pair<String, JSONValue>, String>? {
        val pair = parseString(json)
        if (pair == null) {
            return null
        }
        val key = pair!!.first.string
        var currentString = pair!!.second.trimStart()
        if (currentString[0] != ':') {
            return null
        }
        currentString = currentString.subSequence(1, currentString.length).toString().trimStart()
        val value = parseJson(currentString)
        if (value == null) {
            return null
        }
        return Pair(Pair(key, value.first), value.second)
    }

    fun parseJson(json: String): Pair<JSONValue, String>? {
        var pair: Pair<JSONValue, String>? = Pair(JSONNull(), "")
        pair = parseNull(json)
        if (pair != null) {
            return pair
        }
        pair = parseBoolean(json)
        if (pair != null) {
            return pair
        }
        pair = parseNumber(json)
        if (pair != null) {
            return pair
        }
        pair = parseString(json)
        if (pair != null) {
            return pair
        }
        pair = parseArray(json)
        if (pair != null) {
            return pair
        }
        pair = parseObject(json)
        if (pair != null) {
            return pair
        }
        return null
    }

}