package com.example.tbcexercises

fun main() {

    var gameOn: Boolean = true
    val answers: Array<String> = arrayOf("y", "yes", "n", "no", "დიახ", "არა")
    while (gameOn) {
        println("dawere teqsti")
        val x: String = readln()


        println("dawere teqsti2")
        val y: String = readln()

        var z: Number? = null

        try {
            z = x.getOnlyNumber().toDouble() / y.getOnlyNumber().toDouble()
        } catch (e: Exception) {
            println(e)
        }
        println("X და Y განაყოფი არის: $z")

        println("გსურთ პროგრამის ხელახლა დაწყება <Y/N>?")
        var ANSWER: String = readln()
        while (ANSWER.lowercase() !in answers) {
            println("swored airchie jooo an y an n")
            ANSWER = readln()
            println(ANSWER)

        }
        if (ANSWER.uppercase() == "N" || ANSWER=="არა") {
            gameOn = false
        }
    }

}

fun String.getOnlyNumber(): String {
    val array = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    var result: String = ""
    for (ch in this) {
        if (ch in array) {
            result += ch
        }
    }
    if (result == "") return "0"
    return result
}
