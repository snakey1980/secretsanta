package com.snakey.secretsanta.donotuse

import org.apache.commons.collections4.iterators.PermutationIterator
import org.junit.Ignore
import org.junit.Test
import java.util.*
import java.util.stream.Stream
import java.util.stream.StreamSupport

@Ignore
internal class SecretSantaTest {

    @Test
    fun testDraw() {
        println(SecretSanta().draw(4))
    }

    @Test
    fun testDraw2() {
        (1..100_000).forEach { SecretSanta().draw2(4) }
    }

    @Test
    fun testDraw2patterns() {
        val patterns = mutableMapOf<List<Pair<Int, Int>>, Int>()
        (1..100_000).forEach {
            val pattern = SecretSanta().draw2(4)
            patterns[pattern] = patterns.getOrDefault(pattern, 0) + 1
        }
        patterns.entries.sortedByDescending { it.value }.forEach { println(it) }
    }

    @Test
    fun testDraw3patterns() {
        val patterns = mutableMapOf<List<Pair<Int, Int>>, Int>()
        (1..100_000).forEach {
            val pattern = SecretSanta().draw3(4)
            patterns[pattern] = patterns.getOrDefault(pattern, 0) + 1
        }
        patterns.entries.sortedByDescending { it.value }.forEach { println(it) }
    }

    @Test
    fun testDraw4patterns() {
        val patterns = mutableMapOf<List<Pair<Int, Int>>, Int>()
        (1..100_000).forEach {
            val pattern = SecretSanta().draw4(4)
            patterns[pattern] = patterns.getOrDefault(pattern, 0) + 1
        }
        patterns.entries.sortedByDescending { it.value }.forEach { println(it) }
    }

    @Test
    fun testDraw4patternsCount() {
        for (i in 5..7) {
            val patterns = mutableSetOf<List<Pair<Int, Int>>>()
            (1..100_000).forEach {
                val pattern = SecretSanta().draw4(i)
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
            val shuffles = SecretSanta().draw4CountShuffles(i).first
            val elapsed = (System.nanoTime() - start) / 1_000_000
            println("$i players took ${elapsed}ms and performed ${shuffles} shuffles")
        }
    }

    @Test
    fun test16perm() {
        for (i in (1..16)) {
            val start = System.nanoTime()
            val count = countsPermutations((1..i).toList())
            val elapsed = (System.nanoTime() - start) / 1_000_000_000
            println("$i -> $count (took ${elapsed}s)")
        }
    }

    private fun countsPermutations(list: List<Int>) : Long {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(PermutationIterator(list), Spliterator.ORDERED),
                false).count()
    }

    private fun generatePermutations(list: List<Int>) : Stream<List<Int>> {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(PermutationIterator(list), Spliterator.ORDERED),
                false)
    }

    @Test
    fun countDerangements() {
        for (i in (4..11)) {
            val count = generatePermutations((0 until i).toList())
                    .filter { perm -> (0 until i).none { perm[it] == it } }.count()
            println("Found $count derangements of $i elements")
        }
    }

    private fun countcomponents(pairs: List<Pair<Int, Int>>) : Int {
        val visited = mutableSetOf<Int>()
        var result = 0
        while (visited.size < pairs.size) {
            val start = pairs.find { it.first !in visited }!!.first
            var node = start
            while (node !in visited) {
                visited.add(node)
                node = pairs[node - 1].second
            }
            ++result
        }
        return result
    }

    private fun describecomponents(pairs: List<Pair<Int, Int>>) : List<Int> {
        val visited = mutableSetOf<Int>()
        var result = mutableListOf<Int>()
        while (visited.size < pairs.size) {
            var size = 0
            var node = pairs.find { it.first !in visited }!!.first
            while (node !in visited) {
                visited.add(node)
                ++size
                node = pairs[node - 1].second
            }
            result.add(size)
        }
        return result.sorted()
    }

    @Test
    fun testCountComponents() {
        println(countcomponents(listOf(Pair(1, 2), Pair(2, 1), Pair(3, 4), Pair(4, 5), Pair(5, 3), Pair(6, 7), Pair(7, 8), Pair(8, 9), Pair(9, 6))))
        println(describecomponents(listOf(Pair(1, 2), Pair(2, 1), Pair(3, 4), Pair(4, 5), Pair(5, 3), Pair(6, 7), Pair(7, 8), Pair(8, 9), Pair(9, 6))))
        println(describecomponents(listOf(Pair(1, 2), Pair(2, 1))))
        println(describecomponents(listOf(Pair(1, 2), Pair(2, 1), Pair(3, 4), Pair(4, 3))))
    }

    @Test
    fun componentPatterns() {
        val counts = mutableMapOf<Int, Int>()
        val n = 10_000_000
        for (i in (1..n)) {
            val components = countcomponents(SecretSanta().draw4(16))
            counts[components] = counts.getOrDefault(components, 0) + 1
        }
        for ((component, count) in counts.map { it }.sortedBy { it.value }) {
            val padcount = String.format("%1\$7s", count)
            val ratio = String.format("%1.6f", count / n.toDouble())
            val nformatted = String.format("%,d", n)
            println("Found $padcount instances of $component components out of $nformatted total (ratio $ratio)")
        }
    }

    @Test
    fun componentPatterns2() {
        val counts = mutableMapOf<List<Int>, Int>()
        val n = 10_000_000
        for (i in (1..n)) {
            val components = describecomponents(SecretSanta().draw4(16))
            counts[components] = counts.getOrDefault(components, 0) + 1
        }
        for ((component, count) in counts.map { it }.sortedBy { it.value }) {
            val padcount = String.format("%1\$7s", count)
            val ratio = String.format("%1.6f", count / n.toDouble())
            println("$padcount instances of $component components (ratio $ratio)")
        }
    }

    @Test
    fun bignumber() {
        println(Long.MAX_VALUE)
        println(15L * 14L * 13L * 12L * 11L * 10L * 9L * 8L * 7L * 6L * 5L * 4L * 3L * 2L * 1L)
        println(String.format("%1.6f", 1307674368000.0 / 7697064251745.0))
    }

}