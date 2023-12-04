data class Coordinate(val x: Int, val y: Int)

class Day3Part1 {

    fun convertToMatrix(lines: List<String>) = lines.map {
        it.toCharArray()
    }

    fun getSurroundingCoordinates(x: Int, y: Int): List<Coordinate> = run {
        val offsets = listOf(-1, 0, 1)
        offsets.flatMap { offsetX ->
            offsets.map { offsetY ->
                Coordinate(x + offsetX, y + offsetY)
            }
        }
    }

    fun checkIsAdjacent(x: Int, y: Int, matrix: List<CharArray>): Boolean {
        val coordinatesToCheck = getSurroundingCoordinates(x, y)
        for ((i, j) in coordinatesToCheck) {
            try {
                val value = matrix.get(i)[j]

                if (!value.isDigit() && !value.equals('.')) {
                    return true
                }
            } catch (e: Exception) {
            }
        }
        return false
    }

    fun findAdjacentNumbers(matrix: List<CharArray>): List<Long> {
        val adjacentNumbers: MutableList<Long> = mutableListOf()
        matrix.forEachIndexed { indexRow, row ->
            var numberString = ""
            var isAdjacentToSymbol = false

            row.forEachIndexed { indexColumn, value ->
                if (value.isDigit()) {
                    if (!isAdjacentToSymbol) isAdjacentToSymbol = checkIsAdjacent(indexRow, indexColumn, matrix)
                    numberString += value
                } else {
                    if (isAdjacentToSymbol && numberString != "") adjacentNumbers.add(numberString.toLong())
                    isAdjacentToSymbol = false
                    numberString = ""
                }
            }

        }
        return adjacentNumbers.toList()
    }

    fun run(lines: List<String>) {
        val matrix = convertToMatrix(lines)
        println(findAdjacentNumbers(matrix).sum())
    }

}

class Day3Part2 {

    fun run(lines: List<String>) {
        println("TODO")
    }

}

fun main() {
    val lines = readFile("day3/input.txt")
    Day3Part1().run(lines)
    Day3Part2().run(lines)
}