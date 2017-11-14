package com.snakey.secretsanta.donotuse

import org.junit.jupiter.api.Test

internal class SecretSantaTest {

    @Test
    fun testDraw() {
        println(SecretSanta().draw(setOf(1, 2, 3, 4)))
    }

    @Test
    fun testDraw2() {
        (1..100_000).forEach { SecretSanta().draw2(setOf(1, 2, 3, 4)) }
    }

    @Test
    fun testDraw2patterns() {
        val patterns = mutableMapOf<List<Pair<Int, Int>>, Int>()
        (1..100_000).forEach {
            val pattern = SecretSanta().draw2(setOf(1, 2, 3, 4))
            patterns[pattern] = patterns.getOrDefault(pattern, 0) + 1
        }
        patterns.entries.sortedByDescending { it.value }.forEach { println(it) }
    }

    @Test
    fun testDraw3patterns() {
        val patterns = mutableMapOf<List<Pair<Int, Int>>, Int>()
        (1..100_000).forEach {
            val pattern = SecretSanta().draw3(setOf(1, 2, 3, 4))
            patterns[pattern] = patterns.getOrDefault(pattern, 0) + 1
        }
        patterns.entries.sortedByDescending { it.value }.forEach { println(it) }
    }

    @Test
    fun testDraw4patterns() {
        val patterns = mutableMapOf<List<Pair<Int, Int>>, Int>()
        (1..100_000).forEach {
            val pattern = SecretSanta().draw4(setOf(1, 2, 3, 4))
            patterns[pattern] = patterns.getOrDefault(pattern, 0) + 1
        }
        patterns.entries.sortedByDescending { it.value }.forEach { println(it) }
    }

    @Test
    fun testDraw4patternsCount() {
        for (i in 5..7) {
            val patterns = mutableSetOf<List<Pair<Int, Int>>>()
            (1..100_000).forEach {
                val pattern = SecretSanta().draw4((1..i).toSet())
                patterns.add(pattern)
            }
            println("Found ${patterns.size} derangements for $i players")
        }
    }

    @Test
    fun generatePermutations() {
        println(generatePermutations(listOf(1)))
        println(generatePermutations(listOf(1, 2)))
        println(generatePermutations(listOf(1, 2, 3)))
        generatePermutations(listOf(1, 2, 3, 4)).forEach {
            //[(1, 4), (2, 1), (3, 2), (4, 3)]
            val valid = if (it.none { i -> i == it.indexOf(i) + 1 }) "yes" else "no"
            println("[(1, ${it[0]}), (2, ${it[1]}), (3, ${it[2]}), (4, ${it[3]})]   $valid")
        }
    }

    @Test
    fun testTimes() {
        for (i in listOf(10, 100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000)) {
            val start = System.nanoTime()
            val shuffles = SecretSanta().draw4CountShuffles((1..i).toSet()).first
            val elapsed = (System.nanoTime() - start) / 1_000_000
            println("$i players took ${elapsed}ms and performed ${shuffles} shuffles")
        }
    }

    private fun generatePermutations(list: List<Int>) : List<List<Int>> {
        if (list.isEmpty()) {
            throw IllegalArgumentException()
        }
        if (list.size == 1) {
            return listOf(list)
        }
        val result = mutableListOf<List<Int>>()
        list.forEach { i ->
            val subList = list.filter { it != i }.toList()
            val subPermutations = generatePermutations(subList)
            subPermutations.forEach {
                val permutation = it.toMutableList()
                permutation.add(0, i)
                result.add(permutation)
            }
        }
        return result
    }

}