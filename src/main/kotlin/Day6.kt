data class Race(val time: Long, val distance: Long)

class Day6Part1 {

    private val races = mutableListOf<Race>()

    private fun readToRaces(lines: List<String>) {
        val times =
            lines[0].split(": ")[1].split(" ").mapNotNull { if (it == " " || it == "") null else it.trim().toLong() }
        val distances =
            lines[1].split(": ")[1].split(" ").mapNotNull { if (it == " " || it == "") null else it.trim().toLong() }

        times.forEachIndexed { index, time ->
            races.add(Race(time, distances.get(index)))
        }
    }

    private fun processRace(race: Race): Long {
        var count = 0L
        for (time in 0 .. race.time) {
            if ((race.time - time) *  time > race.distance) count += 1
        }
        return count
    }

    fun run(lines: List<String>) {
        readToRaces(lines)
        println(races.map { processRace(it) }.reduce { acc, i -> acc * i })
    }

}

class Day6Part2 {

    private fun processRace(race: Race): Long {
        var count = 0L
        for (time in 0 .. race.time) {
            if ((race.time - time) *  time > race.distance) count += 1
        }
        return count
    }

    private fun processLines(lines: List<String>): Race {
        val time = lines[0].split(": ")[1].replace(" ", "").trim().toLong()
        val distance = lines[1].split(": ")[1].replace(" ", "").trim().toLong()

        return Race(time, distance)
    }

    fun run(lines: List<String>) {
        println(processRace(processLines(lines)))
    }

}

fun main() {
    val lines = readFile("day6/input.txt")
//    Day6Part1().run(lines)
    Day6Part2().run(lines)
}