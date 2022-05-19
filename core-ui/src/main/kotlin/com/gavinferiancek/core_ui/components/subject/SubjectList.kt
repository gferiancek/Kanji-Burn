package com.gavinferiancek.core_ui.components.subject

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_ui.components.EmptyListText

@ExperimentalFoundationApi
@ExperimentalCoilApi
@Composable
fun SubjectList(
    subjects: List<Subject>,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit,
) {
    var numberOfColumns = GridCells.Fixed(4)
    if (subjects.isNotEmpty()) {
        numberOfColumns = when (subjects.first()) {
            is Subject.Radical, is Subject.Kanji -> GridCells.Fixed(4) // 108.dp for 3 items
            else -> GridCells.Fixed(1)
        }
    }

    Crossfade(targetState = subjects.isEmpty()) { isEmpty ->
        when {
            isEmpty -> EmptyListText()
            else -> {
                /**
                 * TODO
                 * Compose is currently bugged, but once 1.2 is stable, move over to stable gridlist
                 * implementation.  item(span = { GridItemSpan(maxSpanCount) }) {} Enables custom
                 * spans so that we can make a header with subjects.groupby { it.level }
                 */
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    cells = numberOfColumns,
                    verticalArrangement = Arrangement.Top
                ) {
                    items(
                        subjects,
                    ) { subject ->
                        SubjectListItem(
                            subject = subject,
                            imageLoader = imageLoader,
                            onSelectSubject = navigateToDetailScreen,
                        )
                    }
                }
            }
        }
    }
}