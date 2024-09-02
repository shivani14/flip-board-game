package com.kpit.flipboardgame.presentation.flipboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kpit.flipboardgame.R
import com.kpit.flipboardgame.domain.model.FlipBoardBoxItem


@Composable
fun FlipBoardScreen(flipBoardViewModel: FlipBoardViewModel) {
    val itemList by flipBoardViewModel.flipBoard.collectAsState()
    val biggestRectangleSize by flipBoardViewModel.biggestRectangleArea.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(FlipBoardViewModel.BOARD_COLUMN_COUNT),
            modifier = Modifier
                .background(Color.LightGray)
                .border(1.dp, Color.Black)
        ) {
            items(itemList.flatten()) { box ->
                BoardBox(item = box) {
                    flipBoardViewModel.updateBoxState(box)
                }
            }
        }
        TextBiggestRectangleSize(size = biggestRectangleSize)
    }
}

@Composable
fun BoardBox(item: FlipBoardBoxItem, toggleBox: () -> Unit) {
    val color = when {
        item.isPartOfBiggestRectangle -> Color.Red
        item.isSelected -> Color.DarkGray
        else -> Color.LightGray
    }

    Box(modifier = Modifier
        .size(40.dp)
        .background(color)
        .border(1.dp, Color.Black)
        .clickable { toggleBox() })
}

@Composable
fun TextBiggestRectangleSize(size: Int) {
    Text(
        text = stringResource(R.string.biggest_rectangle, size),
        modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp
    )
}

