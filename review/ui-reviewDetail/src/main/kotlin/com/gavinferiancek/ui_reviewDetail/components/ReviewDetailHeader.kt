package com.gavinferiancek.ui_reviewDetail.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_ui.components.text.CharacterText
import com.gavinferiancek.core_ui.theme.spacing
import com.gavinferiancek.review_domain.DetailScreenItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@Composable
fun ReviewDetailHeader(
    subject: Subject,
    color: Color,
    scope: CoroutineScope,
    screenItems: List<DetailScreenItem>,
    lazyState: LazyListState,
    imageLoader: ImageLoader,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = color,
        elevation = 10.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.extraSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CharacterText(
                characters = subject.characters,
                style = MaterialTheme.typography.h1,
                imageLoader = imageLoader,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.spacing.small),
                textAlign = TextAlign.Start,
                text = "Go To:",
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onPrimary,
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                screenItems.forEachIndexed { index, item ->
                    item {
                        TextButton(
                            modifier = Modifier
                                .defaultMinSize(minWidth = MaterialTheme.spacing.extraSmall)
                                .padding(
                                    start = if (screenItems.count() > 5) MaterialTheme.spacing.none else MaterialTheme.spacing.small,
                                ),
                            contentPadding = PaddingValues(
                                if (screenItems.count() > 5) MaterialTheme.spacing.small else MaterialTheme.spacing.extraSmall),
                            onClick = {
                                scope.launch {
                                    lazyState.animateScrollToItem(index)
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = color,
                                contentColor = MaterialTheme.colors.onPrimary,
                            ),
                        ) {
                            Text(
                                text = item.headerValue,
                                style = if (screenItems.count() > 5) MaterialTheme.typography.overline else MaterialTheme.typography.caption,
                            )
                        }
                    }
                }
            }
        }
    }
}
