import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import java.io.File
import java.lang.StringBuilder
import java.util.*


/*
Вариант 11 -- transpose
“Транспонирует” входной текст по входящим в него словам, разделённым
последовательностями пробелов, т.е. если входящий файл содержал строки “A B C”
и “D E”, то результатом будет файл со строками “A D”, “B E” и “C”.
● file задаёт имя входного файла. Если параметр отсутствует, следует считывать
текст с консоли.
● Флаг -o ofile задаёт имя выходного файла. Если параметр отсутствует, следует
выводить результаты на консоль.
● Флаг -a num означает, что в выходном тексте каждое слово должно занимать
num символов, а оставшееся место (если оно есть) должно быть заполнено
пробелами.
● Флаг -t означает, что если слово “не влезает” в выделенное для него место
(флагом -а), то его следует обрезать до нужного размера.
● Флаг -r означает, что слово в рамках выделенного для него места (флагом -а)
следует выравнивать по правой границе. Если данный флаг не указан, слово
выравнивается по левой границе.
Command line: transpose [-a num] [-t] [-r] [-o ofile] [file]
В случае, когда какое-нибудь из имён файлов указано неверно, следует выдать
ошибку. В случае, если флаг -а отсутствует, но присутствуют флаги -t или -r, следует
выравнивать текст так, будто указан флаг “-а 10”.
Кроме самой программы, следует написать автоматические тесты к ней.
 */
fun main(args: Array<String>) = start(args)

fun start(args: Array<String>) = Transpose().main(args)

class Transpose: CliktCommand() {

    val num by option("-a").int()
    val outputFile by option("-o")
    val cut by option("-t").flag()
    val byRightSide by option("-r").flag()
    val inputName by argument()

    override fun run() {
        val text = if (inputName.isBlank()) {
            val sb = StringBuilder()
            val scanner = Scanner(System.`in`)
            while (scanner.hasNextLine()) sb.append("${scanner.nextLine()}\n")
            sb.toString().removeSuffix("\n")
        } else File(inputName).readText()
        if (text.isBlank()) return
        val matrix = Matrix(text, transposed = true).applyArgs(num, cut, byRightSide)
        if (outputFile != null) {
            File(outputFile!!).writeText(matrix.toString())
        } else print(matrix.toString())

    }
}
