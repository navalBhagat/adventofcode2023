data class Game(
    val hand: String,
    val bid: Long,
    val cards: MutableList<CARD>? = mutableListOf(),
    var handType: HAND? = null
)

enum class CARD {
    Unknown,
    Two,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,
    Jack,
    Queen,
    King,
    Ace
}

enum class HAND {
    Unknown,
    HighCard,
    OnePair,
    TwoPair,
    ThreeOfAKind,
    FullHouse,
    FourOfAKind,
    FiveOfAKind
}

class Day7Part1 {

    fun findBid(index: Int, game: Game) = (index + 1L) * game.bid

    fun rankGames(games: List<Game>) =
        games.sortedWith(compareByDescending<Game> { it.handType?.ordinal ?: HAND.Unknown.ordinal }
            .thenByDescending { game ->
                game.cards?.maxByOrNull { it.ordinal } ?: CARD.Unknown
            }).reversed()

    fun findMatchingCard(card: String) = when {
        card == "2" -> CARD.Two
        card == "3" -> CARD.Three
        card == "4" -> CARD.Four
        card == "5" -> CARD.Five
        card == "6" -> CARD.Six
        card == "7" -> CARD.Seven
        card == "8" -> CARD.Eight
        card == "9" -> CARD.Nine
        card == "T" -> CARD.Ten
        card == "J" -> CARD.Jack
        card == "Q" -> CARD.Queen
        card == "K" -> CARD.King
        card == "A" -> CARD.Ace
        else -> throw IllegalArgumentException("Invalid card: $card")
    }

    fun determineHand(cards: String): HAND {
        val groupedByCount = cards.groupBy { it }.values.map { it.size }.sorted()

        fun checkHand(vararg counts: Int): Boolean = groupedByCount.toIntArray() contentEquals counts

        return when (groupedByCount.size) {
            1 -> HAND.FiveOfAKind
            2 -> when {
                checkHand(2, 3) || checkHand(3, 2) -> HAND.FullHouse
                checkHand(1, 4) || checkHand(4, 1) -> HAND.FourOfAKind
                else -> throw IllegalArgumentException("Invalid hand: $cards")
            }

            3 -> when {
                checkHand(1, 2, 2) || checkHand(2, 1, 2) || checkHand(2, 2, 1) -> HAND.TwoPair
                checkHand(1, 1, 3) || checkHand(3, 1, 1) -> HAND.ThreeOfAKind
                else -> throw IllegalArgumentException("Invalid hand: $cards")
            }

            4 -> if (checkHand(1, 1, 1, 2)) HAND.OnePair else throw IllegalArgumentException("Invalid hand: $cards")
            5 -> HAND.HighCard
            else -> throw IllegalArgumentException("Invalid hand: $cards")
        }
    }

    fun processGame(game: Game): Game {
        // find cards
        val cards = game.hand.split("").filter { it.isNotBlank() }
        cards.forEach {
            game.cards?.add(findMatchingCard(it))
        }

        // find hand
        game.handType = determineHand(game.hand)

        return game
    }

    fun processLine(line: String) = Game(line.split(" ")[0].trim(), line.split(" ")[1].trim().toLong())

    fun run(lines: List<String>) {
        println(rankGames(lines.map { processGame(processLine(it)) }).mapIndexed { index, game -> findBid(index, game) }.sum())
    }
}

class Day7Part2 {
    fun run(lines: List<String>) {

    }
}

fun main() {
    val lines = readFile("day7/test.txt")
    Day7Part1().run(lines)
    Day7Part2().run(lines)
}