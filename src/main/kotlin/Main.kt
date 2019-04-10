import lowLevelDataTypes.*
import java.io.File

fun main(args: Array<String>){
    val asura = AsuraJson();

    val file = File("./src/test/testCases/array.json")

    val pair = Parser.arrayParse(file.readText())
    println(pair)
    printJsonVal(pair!!.first)
}


fun printJsonVal(value: JSONValue){
    when(value){
        is JSONNull -> println("null")

        is JSONBoolean -> println(value.bool)

        is JSONDouble -> println(value.value)

        is JSONString -> println(value.string)

        is JSONArray -> {
            println("")
            for(i in 1..value.length){
                printJsonVal(value.getAt(i-1)!!)
            }
        }

        else -> println("Bamboozled")
    }
}