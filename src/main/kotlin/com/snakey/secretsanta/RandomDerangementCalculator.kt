package com.snakey.secretsanta

import java.math.BigDecimal
import java.math.MathContext
import java.util.*

object RandomDerangementCalculator {

    private val random = Random()
    private val derangementCounts = mutableMapOf<Int, BigDecimal>()

    private fun derangementCount(n: Int) : BigDecimal {
        return derangementCounts.getOrPut(n, {
            when (n) {
                0 -> BigDecimal.ONE
                1 -> BigDecimal.ZERO
                else -> BigDecimal.valueOf(n.toLong())
                        .minus(BigDecimal.ONE)
                        .multiply(
                                derangementCount(n - 1)
                                .plus(derangementCount(n - 2)))
            }
        })
    }

    private fun Set<Int>.nth(n: Int) : Int {
        if (this.size < n || n < 0) {
            throw IllegalArgumentException()
        }
        var i = 0
        return this.stream().dropWhile { i++ < n }.findFirst().get()
    }

    fun derange(n: Int) : List<Int> {
        val result = (1..n).toMutableList()
        val unmarkedIndices = (0 until n).toMutableSet()
        for (i in n - 1 downTo 0) {
            if (unmarkedIndices.size < 2) {
                break
            }
            if (unmarkedIndices.contains(i)) {
                var j = unmarkedIndices.nth(random.nextInt(unmarkedIndices.size - 1))
                result[i] = result.set(j, result[i])
                val probability = random.nextDouble()
                val threshold =
                        BigDecimal.valueOf((unmarkedIndices.size - 1).toLong())
                                .multiply(derangementCount(unmarkedIndices.size - 2))
                                .divide(derangementCount(unmarkedIndices.size), MathContext.DECIMAL64).toDouble()
                if (probability < threshold) {
                    unmarkedIndices.remove(j)
                }
                unmarkedIndices.remove(i)
            }
        }
        return result
    }
}