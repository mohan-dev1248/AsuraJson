import lowLevelDataTypes.*
import org.junit.Test

import org.junit.Assert.*
import java.io.File

class ParserTest {

    val path:String = "./src/test/testCases/"

    @Test
    fun testNullParseJson(){
        var inputFile = File(path + "null.json")
        var input = inputFile.readText()


        var pair: Pair<JSONValue,String> ?= Parser.nullParse(input)

        assertEquals(true, pair!!.first is JSONNull)

        inputFile = File(path+ "notNull.json")
        input = inputFile.readText()

        pair = Parser.nullParse(input)

        assert(null == pair)
    }

    @Test
    fun testBooleanPareJson(){
        var inputFile = File(path + "true.json")
        var input = inputFile.readText()


        var pair: Pair<JSONValue,String> ?= Parser.booleanParse(input)
        assertEquals(true, pair!!.first is JSONBoolean)

        var obj = pair!!.first as JSONBoolean
        assertEquals(true,obj.bool)

        inputFile = File(path + "false.json")
        input = inputFile.readText()

        pair = Parser.booleanParse(input)
        assertEquals(true, pair!!.first is JSONBoolean)

        obj = pair!!.first as JSONBoolean
        assertEquals(false,obj.bool)

        inputFile = File(path + "notBool.json")
        input = inputFile.readText()

        pair = Parser.booleanParse(input)
        assert(null==pair)
    }

    @Test
    fun testBasicBlocks() {
        var json: JSONValue ?= Parser.parse("[]")

        assertEquals(true,json is JSONArray)

        json = Parser.parse("{}")
        assertEquals(true, json is JSONObject)

        json = Parser.parse("\"A JSON payload should be an object or array, not a string.\"")
        assert(json==null)

        json = Parser.parse("[\"Unclosed array\"")
        assert(json==null)

        json = Parser.parse("{\"Unclosed object\"")
        assert(json==null)
    }

    @Test
    fun testObject(){
        var json: JSONValue ?= Parser.parse("{unquoted_key: \"keys must be quoted\"}")

        assert(json==null)

        json = Parser.parse("{\"FirstName\": \"Mohanakrishna\"}")
        assertEquals(true, json is JSONObject)
    }


}