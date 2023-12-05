data class MapForSeed(val seedStart: Long, val destinationStart: Long, val range: Long)

data class SeedPair(val seedStart: Long, val range: Long)

fun parseMaps(lines: List<String>, startIndex: Int, endIndex: Int) =
    lines.subList(startIndex, endIndex - 1).mapNotNull {
        if (it == " " || it == "") null
        val (destinationStart, seedStart, range) = it.trim().split(" ").map { str -> str.toLong() }
        MapForSeed(seedStart, destinationStart, range)
    }

val seeds = mutableListOf<Long>()
val seedPairs = mutableListOf<SeedPair>()
val seedToSoilMap = mutableListOf<MapForSeed>()
val soilToFertilizerMap = mutableListOf<MapForSeed>()
val fertilizerToWaterMap = mutableListOf<MapForSeed>()
val waterToLightMap = mutableListOf<MapForSeed>()
val lightToTempMap = mutableListOf<MapForSeed>()
val tempToHumidityMap = mutableListOf<MapForSeed>()
val humidityToLocMap = mutableListOf<MapForSeed>()

fun processLines(lines: List<String>) {
    seeds.addAll(lines.first().split(": ")[1].split(" ").map { it.toLong() }.toList())

    // Extract seed-to-soil map
    seedToSoilMap.addAll(parseMaps(lines, 3, lines.indexOf("soil-to-fertilizer map:")))

    // Extract soil-to-fertilizer map
    soilToFertilizerMap.addAll(
        parseMaps(
            lines,
            lines.indexOf("soil-to-fertilizer map:") + 1,
            lines.indexOf("fertilizer-to-water map:")
        )
    )

    // Extract fertilizer-to-water map
    fertilizerToWaterMap.addAll(
        parseMaps(
            lines,
            lines.indexOf("fertilizer-to-water map:") + 1,
            lines.indexOf("water-to-light map:")
        )
    )

    // Extract water-to-light map
    waterToLightMap.addAll(
        parseMaps(
            lines,
            lines.indexOf("water-to-light map:") + 1,
            lines.indexOf("light-to-temperature map:")
        )
    )

    // Extract light-to-temp map
    lightToTempMap.addAll(
        parseMaps(
            lines,
            lines.indexOf("light-to-temperature map:") + 1,
            lines.indexOf("temperature-to-humidity map:")
        )
    )

    // Extract temp-to-humidity map
    tempToHumidityMap.addAll(
        parseMaps(
            lines,
            lines.indexOf("temperature-to-humidity map:") + 1,
            lines.indexOf("humidity-to-location map:")
        )
    )

    // Extract humidity-to-loc map
    humidityToLocMap.addAll(
        parseMaps(
            lines,
            lines.indexOf("humidity-to-location map:") + 1,
            lines.size + 1
        )
    )

}

fun parseMapForSeed(seed: Long, mapForSeed: MapForSeed): Long? {
    val listOfSeeds = mapForSeed.seedStart..(mapForSeed.seedStart + mapForSeed.range)
    return if (seed in listOfSeeds) {
        val index = seed.toInt() - mapForSeed.seedStart.toInt()
        mapForSeed.destinationStart + index
    } else {
        null
    }
}

fun processSeed(seed: Long): Long {
    val soil = seedToSoilMap.mapNotNull { parseMapForSeed(seed, it) }.ifEmpty { listOf(seed) }
    val fertilizer = soilToFertilizerMap.mapNotNull { parseMapForSeed(soil.first(), it) }.ifEmpty { soil }
    val water = fertilizerToWaterMap.mapNotNull { parseMapForSeed(fertilizer.first(), it) }.ifEmpty { fertilizer }
    val light = waterToLightMap.mapNotNull { parseMapForSeed(water.first(), it) }.ifEmpty { water }
    val temp = lightToTempMap.mapNotNull { parseMapForSeed(light.first(), it) }.ifEmpty { light }
    val humidity = tempToHumidityMap.mapNotNull { parseMapForSeed(temp.first(), it) }.ifEmpty { temp }
    val loc = humidityToLocMap.mapNotNull { parseMapForSeed(humidity.first(), it) }.ifEmpty { humidity }

    return loc.first()
}

class Day5Part1 {

    fun run() {
        println(seeds.minOfOrNull { processSeed(it) })
    }
}

class Day5Part2 {

    fun processSeedPair(pair: SeedPair): List<Long> {
        println("pair process: $pair")
        val seedStart = pair.seedStart
        val range = pair.range

        // Use a sequence directly
        return generateSequence(seedStart) { it + 1 }
            .takeWhile { it <= seedStart + range }
            .map {
                println("processing seed: $it")
                processSeed(it)
            }
            .toList()
    }

    fun processSeeds() {
        for (index in 0..<seeds.size step 2) {
            seedPairs.add(SeedPair(seeds[index], seeds[index + 1]))
        }
    }

    fun run() {
        processSeeds()
        println(seedPairs)
        println(seedPairs.map { processSeedPair(it) }.flatMap { it }.minOrNull())
    }
}

fun main() {
    val lines = readFile("day5/input.txt")
    processLines(lines)
    Day5Part1().run()
//    Day5Part2().run()
}