package com.gavinferiancek.ui_reviewList.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.gavinferiancek.theme.spacing

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
        color = MaterialTheme.colors.primaryVariant,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .padding(start = MaterialTheme.spacing.small),
                value = query,
                onValueChange = { query ->
                    onQueryChanged(query)
                    onExecuteSearch()
                },
                label = {
                    Text(
                        text = "Search",
                        color = MaterialTheme.colors.onPrimary,
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
                        tint = MaterialTheme.colors.onPrimary,
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onPrimary,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    unfocusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                    focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                    cursorColor = MaterialTheme.colors.onPrimary,
                ),
            )
            IconButton(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small),
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