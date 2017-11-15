package com.snakey.secretsanta

import org.junit.*
import org.junit.Assert.assertEquals

internal class SecretSantaTest {

    private fun testParticipants(n: Int) : List<SecretSanta.Participant> =
        (1..n).map { SecretSanta.Participant("$it", "$it@example.com") }

    @Test
    fun test() {
        val participants = testParticipants(4)
        val draw = SecretSanta().draw(participants)
        assertEquals(participants.size, draw.size)
        println(describeCycles(draw))
    }

    @Test
    fun cycleStats() {
        val numParticipants = 10
        val numTrials = 10000
        val secretSanta = SecretSanta()
        val participants = testParticipants(numParticipants)
        val cycleCounts = (1..numParticipants).map { Pair(it, 0) }.toMap().toMutableMap()
        for (i in 1..numTrials) {
            describeCycles(secretSanta.draw(participants)).forEach { cycle ->
                val size = cycle.size
                cycleCounts[size] = cycleCounts[size]!! + 1
            }
        }
        println(cycleCounts)
    }

    @Test
    fun cycleStats2() {
        val numParticipants = 4
        val numTrials = 1000000
        val secretSanta = SecretSanta()
        val participants = testParticipants(numParticipants)
        val patternCounts = mutableMapOf<List<List<String>>, Int>()
        for (i in 1..numTrials) {
            val pattern = describeCycles(secretSanta.draw(participants))
            patternCounts.putIfAbsent(pattern, 0)
            patternCounts[pattern] = patternCounts[pattern]!! + 1;
        }
        println(patternCounts)
    }

    @Test
    fun cycleStats3() {
        val numParticipants = 4
        val numTrials = 100000
        val secretSanta = SecretSanta()
        val participants = testParticipants(numParticipants)
        val patternCounts = mutableMapOf<List<List<String>>, Int>()
        for (i in 1..numTrials) {
            var pattern : List<List<String>>
            do {
                pattern = describeCycles(secretSanta.draw(participants))
            } while (pattern.size > 1)
            patternCounts.putIfAbsent(pattern, 0)
            patternCounts[pattern] = patternCounts[pattern]!! + 1
        }
        println(patternCounts)
    }

    fun describeCycles(draw: List<Pair<SecretSanta.Participant, SecretSanta.Participant>>): List<List<String>> {
        val seen: MutableSet<SecretSanta.Participant> = mutableSetOf()
        val result: MutableList<List<SecretSanta.Participant>> = mutableListOf()
        draw.forEach { pair ->
            if (pair.first !in seen) {
                val cycleBuilder = mutableListOf<SecretSanta.Participant>()
                var node = pair.first
                var next = pair.second
                while (!(node in seen)) {
                    seen.add(node)
                    cycleBuilder.add(node)
                    val nextLink = draw.find { p ->
                        p.first == next
                    }
                    node = next
                    next = nextLink!!.second
                }
                result.add(cycleBuilder.toList())
            }
        }
        return result.toList().map { it.map { it.name } }
    }

}