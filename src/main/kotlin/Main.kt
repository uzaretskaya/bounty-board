package ru.uzaretskaya

var playerLevel = 0
const val HERO_NAME = "Madrigal"

fun main() {
    println("$HERO_NAME announces her presence to the world.")
    println("What level is $HERO_NAME?")

    playerLevel = readlnOrNull()?.toIntOrNull() ?: 0
    println("$HERO_NAME's level is $playerLevel.")

    readBountyBoard()

    println("Time passes...")
    println("$HERO_NAME returns from her quest.")

    playerLevel += 1
    println(playerLevel)
    readBountyBoard()
}

private fun readBountyBoard() {
    val message = try {
        obtainQuest(playerLevel)?.replace("Nogartse", "xxxxxxxxx")?.let {
            println(
                """
                $HERO_NAME approaches the bounty board. It reads:
                    "$it"
                """.trimIndent()
            )
        } ?: "$HERO_NAME approaches the bounty board, but it is blank."
    } catch (e: Exception) {
        "$HERO_NAME can't read what's on the bounty board."
    }
    println(message)
}

private fun obtainQuest(
    playerLevel: Int,
    playerClass: String = "paladin",
    hasBefriendedBarbarians: Boolean = true,
    hasAngeredBarbarians: Boolean = false
): String? {
    if (playerLevel <= 0) {
        throw InvalidPlayerLevelException()
    }

    return when (playerLevel) {
        1 -> "Meet Mr. Bubbles in the land of soft things."
        in 2..5 -> {
            val canTalkToBarbarians = !hasAngeredBarbarians && (hasBefriendedBarbarians || playerClass == "barbarian")

            if (canTalkToBarbarians)
                "Convince the barbarians to call off their invasion."
            else
                "Save the town from the barbarian invasions."
        }

        6 -> "Locate the enchanted sword."
        7 -> "Recover the long-lost artifact of creation."
        8 -> "Defeat Nogartse, bringer of death and eater of worlds."
        //else -> "There are no quests right now."
        else -> null
    }
}

class InvalidPlayerLevelException() : IllegalArgumentException("Player level must be at least 1.")
