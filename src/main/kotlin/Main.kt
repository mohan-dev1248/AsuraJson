import lowLevelDataTypes.*
import java.io.File

fun main(args: Array<String>) {
    val asura = AsuraJson();

    val file = File("./src/test/testCases/redit.json")
    val pair = Parser.parseJson(file.readText())

    println(asura.serialize(pair!!.first))
}