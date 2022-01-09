package com.gavinferiancek.ui_subjectlist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.gavinferiancek.components.BaseScreen

@Composable
fun SubjectListScreen(
    state: SubjectListState,
    events: (SubjectListEvents) -> Unit,
) {
    BaseScreen(
        progressBarState = state.progressBarState,
    ) {
        if (state.subjects.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "空っぽ",
                    fontSize = 36.sp,
                    color = Color.DarkGray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.subjects) { subject ->
                    subject.characters?.let { characters ->
                        Text(
                            textAlign = TextAlign.Center,
                            text = characters,
                        )
                    }
                }
            }
        }
    }
}