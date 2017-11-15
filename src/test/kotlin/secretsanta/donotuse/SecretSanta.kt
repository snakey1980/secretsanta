package com.snakey.secretsanta.donotuse

import java.util.*

/*
Bad and buggy implementations of Secret Santa preserved here for posterity
 */
class SecretSanta {

    fun draw(n: Int) : List<Pair<Int, Int>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n).toSet()
        val pot = players.toMutableList()
        Collections.shuffle(pot)
        val result = mutableListOf<Pair<Int, Int>>()
        players.forEach {
            while (pot[0] == it) {
                Collections.shuffle(pot)
            }
            result.add(Pair(it, pot.removeAt(0)))
        }
        return result
    }

    fun draw2(n: Int) : List<Pair<Int, Int>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n).toSet()
        class BadPotException : RuntimeException()
        while (true) {
            val pot = players.toMutableList()
            Collections.shuffle(pot)
            try {
                val result = mutableListOf<Pair<Int, Int>>()
                players.forEach {
                    if (pot.size == 1 && pot[0] == it) {
                        throw BadPotException()
                    }
                    while (pot[0] == it) {
                        Collections.shuffle(pot)
                    }
                    result.add(Pair(it, pot.removeAt(0)))
                }
                return result
            }
            catch (e: BadPotException) {
                // ok, try again
            }
        }
    }

    fun draw3(n: Int) : List<Pair<Int, Int>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n).toSet()
        class BadPotException : RuntimeException()
        while (true) {
            val pot = players.toMutableList()
            Collections.shuffle(pot)
            try {
                val result = mutableListOf<Pair<Int, Int>>()
                players.forEach {
                    if (pot[0] == it) {
                        throw BadPotException()
                    }
                    result.add(Pair(it, pot.removeAt(0)))
                }
                return result
            }
            catch (e: BadPotException) {
                // ok, try again
            }
        }
    }

    fun draw4(n: Int) : List<Pair<Int, Int>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n).toSet()
        val permutation = players.toMutableList()
        do Collections.shuffle(permutation)
            while (players.any { it == permutation[it - 1] })
        return players.map { Pair(it, permutation[it - 1]) }
    }

    fun draw4CountShuffles(n: Int) : Pair<Int, List<Pair<Int, Int>>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n).toSet()
        val permutation = players.toMutableList()
        var shuffles = 0
        do {
            shuffles++
            Collections.shuffle(permutation)
        }
        while (players.any { it == permutation[it - 1] })
        return Pair(shuffles, players.map { Pair(it, permutation[it - 1]) })
    }

}