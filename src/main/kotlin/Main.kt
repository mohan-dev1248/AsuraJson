import java.io.File

fun main(args: Array<String>){
    val asura = AsuraJson();

    val file = File("./src/test/testCases/array.json")

    println(Parser.arrayParse(file.readText()))
}