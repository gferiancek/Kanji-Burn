package com.gavinferiancek.core_ui.components.subject

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_ui.R
import com.gavinferiancek.core_ui.theme.spacing

/**
 * Composable that takes in a list of Subjects and displays them in a Row with a "+" inbetween them.
 * (These are Components that are used to make up the subject, so they all add to make the subject.)
 *
 * @param components The list of components to be displayed in the row
 * @param imageLoader Necessary to display images for Radicals that do not have text
 * @param navigateToDetailScreen Navigates to the detail screen of the clicked Subject.
 */
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun ComponentsList(
    components: List<Subject>,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.spacing.extraSmall,
                end = MaterialTheme.spacing.extraSmall,
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 99.9% of the subjects in the Wanikani API have 4 or less ComponentSubjectIds. We can
        // use that to our advantage by doing screenWidth / 5 (we increase from 4 to 5 to account
        // for the space occupied by "+" Text Composable) to make almost all ComponentLists fit onto
        // the user's device nicely.  If the user manages to visit one of the few out of 9000+
        // that has 5 ComponentSubjectIds, then we are inside of a LazyRow and can scroll the last
        // one into view.
        itemsIndexed(components) { index, subject ->
            Column(
                modifier = Modifier
                    .width(screenWidth / 5),
            ) {
                SubjectListItem(
                    subject = subject,
                    imageLoader = imageLoader,
                    onSelectSubject = navigateToDetailScreen
                )
            }
            if (index < components.count() - 1) {
                Text(
                    text = stringResource(id = R.string.componentsList_plus),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground,
                )
            }
        }
    }
}