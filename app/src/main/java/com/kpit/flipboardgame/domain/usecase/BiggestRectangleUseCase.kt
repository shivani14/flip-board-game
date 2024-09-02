package com.kpit.flipboardgame.domain.usecase

import com.kpit.flipboardgame.domain.model.FlipBoardBoxItem
import com.kpit.flipboardgame.domain.model.BiggestRectangleResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BiggestRectangleUseCase {

    fun calculateBiggestRectangleArea(fields: List<List<FlipBoardBoxItem>>): Flow<BiggestRectangleResult> =
        flow {
            val result = findMaxRectangleArea(fields)
            emit(result)
        }

    private fun findMaxRectangleArea(fields: List<List<FlipBoardBoxItem>>): BiggestRectangleResult {
        if (fields.isEmpty()) return BiggestRectangleResult(emptyList(), 0)

        val n = fields.size
        val m = fields[0].size

        val height = IntArray(m) { 0 }
        val left = IntArray(m) { 0 }
        val right = IntArray(m) { m }
        var maxArea = 0
        var bestCoords: Pair<Pair<Int, Int>, Pair<Int, Int>>? = null

        for (i in 0 until n) {
            var currentLeft = 0
            var currentRight = m

            for (j in 0 until m) {
                height[j] = if (fields[i][j].isSelected) height[j] + 1 else 0
            }

            for (j in 0 until m) {
                if (fields[i][j].isSelected) {
                    left[j] = maxOf(left[j], currentLeft)
                } else {
                    left[j] = 0
                    currentLeft = j + 1
                }
            }

            for (j in m - 1 downTo 0) {
                if (fields[i][j].isSelected) {
                    right[j] = minOf(right[j], currentRight)
                } else {
                    right[j] = m
                    currentRight = j
                }
            }


            for (j in 0 until m) {
                val area = (right[j] - left[j]) * height[j]
                if (area > maxArea) {
                    maxArea = area
                    bestCoords = Pair(Pair(left[j], i - height[j] + 1), Pair(right[j] - 1, i))
                }
            }
        }

        return bestCoords?.let {
            val coordinates = generateRectangleCoordinates(it.first, it.second)
            BiggestRectangleResult(coordinates, maxArea)
        } ?: BiggestRectangleResult(emptyList(), 0)
    }

    private fun generateRectangleCoordinates(
        topLeft: Pair<Int, Int>,
        bottomRight: Pair<Int, Int>
    ): List<Pair<Int, Int>> {
        val coordinates = mutableListOf<Pair<Int, Int>>()
        for (row in topLeft.second..bottomRight.second) {
            for (column in topLeft.first..bottomRight.first) {
                coordinates.add(Pair(row, column))
            }
        }
        return coordinates
    }
}
