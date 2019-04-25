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


        var jsonValue: JSONValue?= Parser.parse(input)

        assertEquals(true, jsonValue!! is JSONNull)

        inputFile = File(path+ "notNull.json")
        input = inputFile.readText()

        jsonValue = Parser.parse(input)

        assert(null == jsonValue)
    }

    @Test
    fun testBooleanPareJson(){
        var inputFile = File(path + "true.json")
        var input = inputFile.readText()


        var jsonValue: JSONValue?= Parser.parse(input)
        assertEquals(true, jsonValue!! is JSONBoolean)

        var obj = jsonValue!! as JSONBoolean
        assertEquals(true,obj.bool)

        inputFile = File(path + "false.json")
        input = inputFile.readText()

        jsonValue = Parser.parse(input)
        assertEquals(true, jsonValue!! is JSONBoolean)

        obj = jsonValue!! as JSONBoolean
        assertEquals(false,obj.bool)

        inputFile = File(path + "notBool.json")
        input = inputFile.readText()

        jsonValue = Parser.parse(input)
        assert(null==jsonValue)
    }

    @Test
    fun testBasicBlocks() {
        var json: JSONValue?= Parser.parse("[]")

        assertEquals(true,json!! is JSONArray)

        json = Parser.parse("{}")
        assertEquals(true, json!! is JSONObject)

        json = Parser.parse("[\"Unclosed array\"")
        assert(json==null)

        json = Parser.parse("{\"Unclosed object\"")
        assert(json==null)
    }

    @Test
    fun testObject(){
        var json: JSONValue? = Parser.parse("{unquoted_key: \"keys must be quoted\"}")

        assert(json==null)

        json = Parser.parse("{\"FirstName\": \"Mohanakrishna\"}")
        assertEquals(true, json!! is JSONObject)
    }
}