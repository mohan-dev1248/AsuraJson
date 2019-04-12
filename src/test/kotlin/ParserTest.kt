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


        var pair: Pair<JSONValue,String> ?= Parser.parseJson(input)

        assertEquals(true, pair!!.first is JSONNull)

        inputFile = File(path+ "notNull.json")
        input = inputFile.readText()

        pair = Parser.parseJson(input)

        assert(null == pair)
    }

    @Test
    fun testBooleanPareJson(){
        var inputFile = File(path + "true.json")
        var input = inputFile.readText()


        var pair: Pair<JSONValue,String> ?= Parser.parseJson(input)
        assertEquals(true, pair!!.first is JSONBoolean)

        var obj = pair!!.first as JSONBoolean
        assertEquals(true,obj.bool)

        inputFile = File(path + "false.json")
        input = inputFile.readText()

        pair = Parser.parseJson(input)
        assertEquals(true, pair!!.first is JSONBoolean)

        obj = pair!!.first as JSONBoolean
        assertEquals(false,obj.bool)

        inputFile = File(path + "notBool.json")
        input = inputFile.readText()

        pair = Parser.parseJson(input)
        assert(null==pair)
    }

    @Test
    fun testBasicBlocks() {
        var json: Pair<JSONValue, String> ?= Parser.parseJson("[]")

        assertEquals(true,json!!.first is JSONArray)

        json = Parser.parseJson("{}")
        assertEquals(true, json!!.first is JSONObject)

        json = Parser.parseJson("[\"Unclosed array\"")
        assert(json==null)

        json = Parser.parseJson("{\"Unclosed object\"")
        assert(json==null)
    }

    @Test
    fun testObject(){
        var json: Pair<JSONValue, String>? = Parser.parseJson("{unquoted_key: \"keys must be quoted\"}")

        assert(json==null)

        json = Parser.parseJson("{\"FirstName\": \"Mohanakrishna\"}")
        assertEquals(true, json!!.first is JSONObject)
    }


}