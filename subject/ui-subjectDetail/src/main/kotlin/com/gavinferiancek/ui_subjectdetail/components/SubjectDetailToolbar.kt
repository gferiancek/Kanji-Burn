package com.gavinferiancek.ui_subjectdetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.gavinferiancek.ui_subjectdetail.R
import com.gavinferiancek.ui_subjectdetail.ui.SubjectDetailState

@Composable
fun SubjectDetailToolbar(
    state: SubjectDetailState,
    color: Color,
    onNavigateUp: () -> Unit,
) {
    state.subject?.let {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text(
                            modifier = Modifier
                                .padding(
                                    top = 4.dp,
                                    bottom = 4.dp,
                                    start = 8.dp,
                                    end = 8.dp,
                                ),
                            text = "Level ${state.subject.level}",
                            color = KanjiBurnTheme.colors.onPrimary,
                        )
                    }
                    Row {
                        Text(
                            modifier = Modifier
                                .padding(
                                    end = 8.dp
                                )
                                .align(Alignment.CenterVertically),
                            text = "Apprentice IV",
                            color = KanjiBurnTheme.colors.onPrimary,
                        )
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(0.75f),
                            painter = painterResource(id = R.drawable.apprentice),
                            contentDescription = null,
                        )
                    }

                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onNavigateUp
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back button to navigate to previous page",
                        tint = KanjiBurnTheme.colors.onPrimary,
                    )
                }
            },
            backgroundColor = color,
            elevation = 0.dp
        )
    }
}