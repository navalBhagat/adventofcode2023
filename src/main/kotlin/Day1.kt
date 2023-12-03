import java.io.File

fun readFile(uri: String) = File(object {}.javaClass.getResource(uri).toURI()).readLines()

class Day1Part1 {
    fun processLine(line: String) = line.toCharArray().filter { it.isDigit() }.map { it.toString() }
    fun value(numbers: List<String>) = "${numbers.get(0)}${numbers.get(numbers.size - 1)}".toInt()
    fun run(lines: List<String>) = println(lines.map { value(processLine(it)) }.sum())
}

class Day1Part2 {

    val numberStrings = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun findIndexesAndMapValues(mainString: String, subString: String, value: Int): Map<Int, Int> {
        val indexes = mutableListOf<Int>()

        var index = mainString.indexOf(subString)
        while (index >= 0) {
            indexes.add(index)
            index = mainString.indexOf(subString, index + 1)
        }

        return indexes.associateWith { value }
    }

    fun processLine(line: String): List<String> {
        val indices = mutableMapOf<Int, Int>()
        numberStrings.keys.forEach {
            if (line.contains(it)) {
                indices += findIndexesAndMapValues(line, it, numberStrings[it]!!)
            }
        }

        println(line)

        val processedline = line.toCharArray().mapIndexed { index, char ->
            if (indices.keys.contains(index)) {
                "${indices[index]}${char}"
            } else {
                "${char}"
            }
        }.joinToString("")

        println(processedline)

        return processedline.toCharArray().filter { it.isDigit() }.map { it.toString() }
    }

    fun value(numbers: List<String>) = "${numbers.first()}${numbers.last()}".toInt()

    fun run(lines: List<String>) = println(lines.map { value(processLine(it)) }.sum())
}

fun main() {
    val lines = readFile("/day1/input.txt")
    Day1Part1().run(lines)
    Day1Part2().run(lines)
}