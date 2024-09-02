package com.kpit.flipboardgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.kpit.flipboardgame.domain.usecase.BiggestRectangleUseCase
import com.kpit.flipboardgame.factories.FlipBoardViewModelFactory
import com.kpit.flipboardgame.presentation.flipboard.FlipBoardScreen
import com.kpit.flipboardgame.presentation.flipboard.FlipBoardViewModel
import com.kpit.flipboardgame.ui.theme.FlipBoardGameTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlipBoardGameTheme {
                val flipBoardViewModel by viewModels<FlipBoardViewModel>() {
                    FlipBoardViewModelFactory(BiggestRectangleUseCase())
                }

                // A surface container using the 'background' color from the theme
                FlipBoardScreen(flipBoardViewModel = flipBoardViewModel)
            }
        }
    }
}