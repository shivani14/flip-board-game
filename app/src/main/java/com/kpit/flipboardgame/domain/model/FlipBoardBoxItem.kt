package com.kpit.flipboardgame.domain.model

data class FlipBoardBoxItem(
    val row: Int,
    val column: Int,
    var isSelected: Boolean = false,
    var isPartOfBiggestRectangle: Boolean = false
)
