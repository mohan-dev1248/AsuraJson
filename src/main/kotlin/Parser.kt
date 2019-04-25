import lowLevelDataTypes.*

object Parser {
    private val regex =
        "^(-?[1-9][0-9]*(([.][0-9]+)?([eE][+-]?[0-9]+)?)?)|^(-?0[.][0-9]+([eE][+-]?[0-9]+)?)|^(0)".toRegex()

    private fun parseNull(json: String) = if (!json.startsWith("null")) null
                                            else Pair(JSONNull(), json.subSequence(4, json.length).toString())

    private fun parseBoolean(json: String): Pair<JSONBoolean, String>? {
        if (json.startsWith("true"))
            return Pair(JSONBoolean(true), json.subSequence(4, json.length).toString())
        if (json.startsWith("false"))
            return Pair(JSONBoolean(false), json.subSequence(5, json.length).toString())
        return null
    }

    private fun parseNumber(json: String): Pair<JSONValue, String>? {
        val obj = regex.find(json)?.value ?: return null
        return Pair(JSONDouble(obj.toDouble()), regex.split(json, 2)[1])
    }

    private fun parseString(json: String): Pair<JSONString, String>? {
        if (!json.startsWith("\"")) return null
        var string = ""
        var parsedLength = 1
        while (parsedLength < json.length && json[parsedLength] != '\"') {
            val list = arrayListOf('\t', '\r', '\b', '\n', (12).toChar())
            if (list.contains(json[parsedLength])) return null
            if (json[parsedLength] == '\\') {
                val char = if (parsedLength + 1 < json.length) {
                    when (json[parsedLength + 1]) {
                        'b' -> '\b'
                        '\"' -> '\"'
                        '\\' -> '\\'
                        'n' -> '\n'
                        'r' -> '\r'
                        't' -> '\t'
                        '/' -> '/'
                        //Unable to put \f in Kotlin since its showing illegal escape sequence
                        'f' -> (12).toChar()
                        'u' -> {
                            parsedLength += 4
                            json.subSequence(parsedLength - 2, parsedLength + 2).toString().toInt(radix = 16).toChar()
                        }
                        else -> null
                    }
                } else null
                if (char == null) return null
                string += char.toString()
                parsedLength += 2
                continue

            }
            string += json[parsedLength++].toString()
        }
        if (parsedLength + 1 > json.length) {
            return null
        }
        return Pair(JSONString(string), json.subSequence(parsedLength + 1, json.length).toString())
    }

    private fun parseArray(json: String): Pair<JSONArray, String>? {
        if (!json.startsWith("[")) return null
        val jsonArray = JSONArray()
        var currentString = json.subSequence(1, json.length).toString()
        do {
            val pair = parseJson(currentString.trimStart())
            if (pair == null) {
                if (currentString.trimStart()[0] == ']') {
                    return Pair(
                        jsonArray,
                        currentString.trimStart().subSequence(1, currentString.trimStart().length).toString()
                    )
                }
                return null
            }
            jsonArray.add(pair.first)
            if (pair.second.trimStart().startsWith(",")) {
                currentString =
                    pair.second.trimStart().subSequence(1, pair.second.trimStart().length).toString()
                continue
            }
            if (pair.second.trimStart().startsWith("]"))
                return Pair(
                    jsonArray,
                    pair.second.trimStart().subSequence(1, pair.second.trimStart().length).toString()
                )
            return null
        } while (currentString.isNotEmpty())
        return null
    }

    private fun parseObject(json: String): Pair<JSONObject, String>? {
        if (json[0] == '{') {
            val jsonObject = JSONObject()
            var currentString = json.subSequence(1, json.length).toString()
            do {
                val pair = parseKeyValue(currentString.trimStart())
                if (pair == null) {
                    if (currentString.trimStart()[0] == '}') {
                        return Pair(
                            jsonObject,
                            currentString.trimStart().subSequence(1, currentString.trimStart().length).toString()
                        )
                    }
                    return null
                }
                jsonObject.add(pair.first.first, pair.first.second)
                if (pair.second.trimStart().startsWith(",")) {
                    currentString =
                        pair.second.trimStart().subSequence(1, pair.second.trimStart().length).toString()
                    continue
                }
                if (pair.second.trimStart().startsWith("}"))
                    return Pair(
                        jsonObject,
                        pair.second.trimStart().subSequence(1, pair.second.trimStart().length).toString()
                    )
                return null
            } while (currentString.isNotEmpty())
        }
        return null
    }

    private fun parseKeyValue(json: String): Pair<Pair<String, JSONValue>, String>? {
        val pair = parseString(json)
        if (pair == null) return null
        val key = pair.first.string
        var currentString = pair.second.trimStart()
        if (!currentString.startsWith(":")) return null
        currentString = currentString.subSequence(1, currentString.length).toString().trimStart()
        val value = parseJson(currentString)
        if (value == null) return null
        return Pair(Pair(key, value.first), value.second)
    }

    private fun parseJson(json: String): Pair<JSONValue, String>? {
        var pair: Pair<JSONValue, String>? = Pair(JSONNull(), "")
        val parseFunctions =
            arrayOf(::parseNull, ::parseBoolean, ::parseNumber, ::parseString, ::parseArray, ::parseObject)
        for (function in parseFunctions) {
            pair = function(json)
            if (pair != null) return pair
        }
        return null
    }

    fun parse(json: String): JSONValue? {
        val pair = parseJson(json)
        //when pair is null pair?.second throws a NullPointerException
        if (pair != null && pair.second.trim().isEmpty()) return pair.first
        return null
    }
}