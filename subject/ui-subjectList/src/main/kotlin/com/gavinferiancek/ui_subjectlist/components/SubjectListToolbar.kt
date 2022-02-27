package com.gavinferiancek.ui_subjectlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme

@ExperimentalComposeUiApi
@Composable
fun SubjectListToolBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onShowFilterDialog: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = KanjiBurnTheme.colors.primary,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .padding(start = 8.dp),
                value = query,
                onValueChange = { query ->
                    onQueryChanged(query)
                    onExecuteSearch()
                },
                label = {
                    Text(
                        text = "Search",
                        color = KanjiBurnTheme.colors.onPrimary,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onExecuteSearch()
                    },
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search Icon",
                        tint = KanjiBurnTheme.colors.onPrimary,
                    )
                },
                textStyle = TextStyle(
                    color = KanjiBurnTheme.colors.onPrimary,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = KanjiBurnTheme.colors.primary,
                    unfocusedIndicatorColor = KanjiBurnTheme.colors.primary,
                    focusedIndicatorColor = KanjiBurnTheme.colors.primary,
                    cursorColor = KanjiBurnTheme.colors.onPrimary,
                ),
            )
            IconButton(
                modifier = Modifier
                    .padding(8.dp),
                onClick = onShowFilterDialog,
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "Filter Icon"
                )
            }
        }
    }
}