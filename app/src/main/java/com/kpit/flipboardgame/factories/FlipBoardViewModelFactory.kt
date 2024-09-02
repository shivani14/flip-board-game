package com.kpit.flipboardgame.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kpit.flipboardgame.domain.usecase.BiggestRectangleUseCase
import com.kpit.flipboardgame.presentation.flipboard.FlipBoardViewModel

class FlipBoardViewModelFactory(private val biggestRectangleUseCase: BiggestRectangleUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FlipBoardViewModel::class.java)){
            FlipBoardViewModel(biggestRectangleUseCase) as T
        } else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}