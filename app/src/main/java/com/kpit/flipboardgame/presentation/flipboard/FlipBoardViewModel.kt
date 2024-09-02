package com.kpit.flipboardgame.presentation.flipboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpit.flipboardgame.domain.model.FlipBoardBoxItem
import com.kpit.flipboardgame.domain.usecase.BiggestRectangleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlipBoardViewModel(private val biggestRectangleUseCase: BiggestRectangleUseCase) : ViewModel() {

    private val _flipBoard = MutableStateFlow(listOf<List<FlipBoardBoxItem>>())
    val flipBoard: StateFlow<List<List<FlipBoardBoxItem>>> = _flipBoard

    private val _biggestRectangleArea = MutableStateFlow(0)
    val biggestRectangleArea: StateFlow<Int> = _biggestRectangleArea

    init {
        val boxList = List(BOARD_ROW_COUNT) { row ->
            List(BOARD_COLUMN_COUNT) { column ->
                FlipBoardBoxItem(row, column)
            }
        }
        _flipBoard.value = boxList
    }


    /**
     * This function will toggle box state to ON or Off.
     */
    fun updateBoxState(boardBoxItem: FlipBoardBoxItem) {
        _flipBoard.value = _flipBoard.value.map { rowList ->
            rowList.map {
                if (it.row == boardBoxItem.row && it.column == boardBoxItem.column) {
                    it.copy(isSelected = !it.isSelected)
                } else {
                    it
                }
            }
        }
        calculateBiggestRectangleArea()
    }

    private fun calculateBiggestRectangleArea() {
        viewModelScope.launch {
            biggestRectangleUseCase.calculateBiggestRectangleArea(_flipBoard.value).collect { result ->
                _biggestRectangleArea.value = result.area
                _flipBoard.value = _flipBoard.value.map { row ->
                    row.map { boardBox ->
                        if (result.coordinates.contains(Pair(boardBox.row, boardBox.column))) {
                            boardBox.copy(isPartOfBiggestRectangle = true)
                        } else {
                            boardBox.copy(isPartOfBiggestRectangle = false)
                        }
                    }
                }
            }
        }

    }

    companion object {
        const val BOARD_ROW_COUNT = 15
        const val BOARD_COLUMN_COUNT = 15
    }
}