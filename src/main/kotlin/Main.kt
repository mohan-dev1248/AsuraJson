import java.io.File

fun main() {
    val asura = AsuraJson()

    val file = File("./src/test/testCases/string.json")
    val str = file.readText()

    val jsonValue = Parser.parse(str)
    println(jsonValue)

    println(asura.serialize(jsonValue!!))
//    val out = pair!!.first as JSONString
//    println("${out.string}  ${out.string.length}")

//    val obj = asura.fromJson<Double>("256.657")
//
//    println(obj)
}