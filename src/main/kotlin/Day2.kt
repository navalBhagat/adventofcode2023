class Day2Part1 {

    val colorCounts = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    fun processLine(line: String): Int {
        val splitLine = line.split(":")
        val gameId = splitLine[0].split(" ")[1].toInt()

        val feasible = splitLine[1].split(";").all { set ->
            set.split(",").all { color ->
                val (count, colorName) = color.trim().split(" ")
                count.toInt() <= (colorCounts[colorName] ?: 0)
            }
        }

        return if (feasible) gameId else 0
    }

    fun run(lines: List<String>) {
        println(lines.map { processLine(it) }.sum())
    }
}

class Day2Part2 {

    fun processLine(line: String): Long {
        val splitLine = line.split(":")
        var red = 0L
        var green = 0L
        var blue = 0L

        splitLine[1].split(";").forEach { set ->
            set.split(",").forEach { color ->
                val (count, colorName) = color.trim().split(" ")
                when (colorName) {
                    "red" -> red = maxOf(red, count.toLong())
                    "blue" -> blue = maxOf(blue, count.toLong())
                    "green" -> green = maxOf(green, count.toLong())
                }
            }
        }

        return red * green * blue
    }

    fun run(lines: List<String>) {
        println(lines.map { processLine(it) }.sum())
    }
}

fun main() {
    val lines = readFile("/day2/input.txt")
    Day2Part1().run(lines)
    Day2Part2().run(lines)
}