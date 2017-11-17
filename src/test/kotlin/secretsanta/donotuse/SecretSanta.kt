package com.snakey.secretsanta.donotuse

import java.util.*

/*
Bad and buggy implementations of Secret Santa preserved here for posterity
 */
class SecretSanta {

    fun draw(n: Int) : List<Pair<Int, Int>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n)
        val hat = players.toMutableList()
        Collections.shuffle(hat)
        val result = mutableListOf<Pair<Int, Int>>()
        players.forEach {
            while (hat[0] == it) {
                Collections.shuffle(hat)
            }
            result.add(Pair(it, hat.removeAt(0)))
        }
        return result
    }

    fun draw2(n: Int) : List<Pair<Int, Int>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n)
        class BadHatException : RuntimeException()
        while (true) {
            val hat = players.toMutableList()
            Collections.shuffle(hat)
            try {
                val result = mutableListOf<Pair<Int, Int>>()
                players.forEach {
                    if (hat.size == 1 && hat[0] == it) {
                        throw BadHatException()
                    }
                    while (hat[0] == it) {
                        Collections.shuffle(hat)
                    }
                    result.add(Pair(it, hat.removeAt(0)))
                }
                return result
            }
            catch (e: BadHatException) {
                // ok, try again
            }
        }
    }

    fun draw3(n: Int) : List<Pair<Int, Int>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n)
        class BadHatException : RuntimeException()
        while (true) {
            val hat = players.toMutableList()
            Collections.shuffle(hat)
            try {
                val result = mutableListOf<Pair<Int, Int>>()
                players.forEach {
                    if (hat[0] == it) {
                        throw BadHatException()
                    }
                    result.add(Pair(it, hat.removeAt(0)))
                }
                return result
            }
            catch (e: BadHatException) {
                // ok, try again
            }
        }
    }

    fun draw4(n: Int) : List<Pair<Int, Int>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n)
        val permutation = players.toMutableList()
        do Collections.shuffle(permutation)
            while (players.any { it == permutation[it - 1] })
        return players.map { Pair(it, permutation[it - 1]) }
    }

    fun draw4CountShuffles(n: Int) : Pair<Int, List<Pair<Int, Int>>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n)
        val permutation = players.toMutableList()
        var shuffles = 0
        do {
            shuffles++
            Collections.shuffle(permutation)
        }
        while (players.any { it == permutation[it - 1] })
        return Pair(shuffles, players.map { Pair(it, permutation[it - 1]) })
    }

    fun draw5(n: Int) : List<Pair<Int, Int>> {
        if (n < 4) throw IllegalArgumentException()
        val players = (1..n)
        val permutation = players.toMutableList()
        Collections.shuffle(permutation)
        return (0 until n).map {
            Pair(permutation[it], permutation[(it + 1) % n])
        }
    }

    fun shuffle(n: Int, random: Random) : List<Int> {
        val list = (1..n).toMutableList()
        for (i in list.size downTo 2) {
            list.set(i - 1, list.set(random.nextInt(i), list[i - 1]))
        }
        return list
    }

    fun sattolo(n: Int, random: Random) : List<Int> {
        val list = (1..n).toMutableList()
        for (i in list.size downTo 2) {
            list.set(i - 1, list.set(random.nextInt(i - 1), list[i - 1]))
        }
        return list
    }



}