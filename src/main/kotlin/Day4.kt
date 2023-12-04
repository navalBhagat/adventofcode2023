data class Card(val id: Int, val winningNumbers: List<Int>, val currentNumbers: List<Int>)

fun processLine(line: String): Card {
    val cardId = line.split(":")[0].replace(" ", "").split("d")[1].toInt()
    val winningNumbers = mutableListOf<Int>()
    val currentNumbers = mutableListOf<Int>()

    val lists = line.split(":")[1].split("|")
    lists[0].split(" ").forEach {
        if (it.trim() != "") winningNumbers.add(it.trim().toInt())
    }
    lists[1].split(" ").forEach {
        if (it.trim() != "") currentNumbers.add(it.trim().toInt())
    }

    return Card(cardId, winningNumbers.toList(), currentNumbers.toList())
}

class Day4Part1 {

    fun calculatePoints(card: Card) = Math.pow(2.0, (card.currentNumbers.mapNotNull {
        if (card.winningNumbers.contains(it)) it
        else null
    }.size - 1.0)).toInt()

    fun run(lines: List<String>) {
        println(lines.map { calculatePoints(processLine(it)) }.sum())
    }
}

class Day4Part2 {

    val mapOfCardCounts = mutableMapOf<Int, Int>()

    fun processCard(card: Card, highestCardId: Int) {
        val numberOfMatches = card.currentNumbers.mapNotNull {
            if (card.winningNumbers.contains(it)) 1
            else null
        }.size

        var numberOfCopies = 1
        if (mapOfCardCounts[card.id] !== null) {
            numberOfCopies += mapOfCardCounts[card.id]!!
        }
        mapOfCardCounts[card.id] = numberOfCopies

        if (numberOfMatches > 0) {
            for (cardId in card.id + 1..card.id + numberOfMatches) {
                if (cardId <= highestCardId) {
                    if (mapOfCardCounts[cardId] !== null) {
                        mapOfCardCounts[cardId] = mapOfCardCounts[cardId]!! + numberOfCopies
                    } else {
                        mapOfCardCounts[cardId] = numberOfCopies
                    }
                }
            }
        }
    }

    fun run(lines: List<String>) {
        val listOfCards = lines.map {
            processLine(it)
        }
        listOfCards.map { processCard(it, listOfCards.size) }
        println(mapOfCardCounts.values.sum())
    }
}

fun main() {
    val lines = readFile("day4/input.txt")
    Day4Part1().run(lines)
    Day4Part2().run(lines)
}