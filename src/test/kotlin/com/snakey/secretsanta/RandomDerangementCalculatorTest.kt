package com.snakey.secretsanta

import com.snakey.secretsanta.donotuse.SecretSanta
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class RandomDerangementCalculatorTest {

    private fun Set<Int>.nth(n: Int) : Int {
        if (this.size < n || n < 0) {
            throw IllegalArgumentException()
        }
        var i = 0
        return this.stream().dropWhile { i++ < n }.findFirst().get()
    }

    @Test
    fun testNth() {
        assertEquals(1, setOf<Int>(1, 2).nth(0))
        assertEquals(2, setOf<Int>(1, 2).nth(1))
    }

    @Test
    fun test() {
        println(RandomDerangementCalculator.derange(4))
    }

    @Test
    fun test4patterns() {
        val patterns = mutableMapOf<List<Int>, Int>()
        (1..100_000).forEach {
            val pattern = RandomDerangementCalculator.derange(4)
            patterns[pattern] = patterns.getOrDefault(pattern, 0) + 1
        }
        patterns.entries.sortedByDescending { it.value }.forEach { println(it) }
    }

    @Test
    fun test5patterns() {
        val patterns = mutableMapOf<List<Int>, Int>()
        (1..100_000).forEach {
            val pattern = RandomDerangementCalculator.derange(5)
            patterns[pattern] = patterns.getOrDefault(pattern, 0) + 1
        }
        patterns.entries.sortedByDescending { it.value }.forEach { println(it) }
    }

    @Test
    fun test5patternsCount() {
        val patterns = mutableSetOf<List<Int>>()
        (1..100_000).forEach {
            val pattern = RandomDerangementCalculator.derange(5)
            patterns.add(pattern)
        }
        println(patterns.size)
    }

}