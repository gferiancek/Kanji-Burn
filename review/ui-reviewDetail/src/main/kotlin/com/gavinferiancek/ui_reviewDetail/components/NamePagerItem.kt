package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gavinferiancek.theme.spacing

@Composable
fun NamePagerItem(
    primaryMeaning: String,
    userSynonyms: String,
    meaningMnemonic: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
            .background(MaterialTheme.colors.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Row() {
                Text(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium,
                        )
                        .alignByBaseline(),
                    text = "PRIMARY",
                    color = Color.Gray,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    modifier = Modifier
                        .alignByBaseline(),
                    text = primaryMeaning,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface,
                    )
            }
            Row() {
                Text(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.spacing.small,
                            end = MaterialTheme.spacing.medium,
                        )
                        .alignByBaseline(),
                    text = "USER SYNONYMS",
                    color = Color.Gray,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    modifier = Modifier
                        .alignByBaseline(),
                    text = userSynonyms,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface,
                    )
            }
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.medium),
                text = "Mnemonic",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                )
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = meaningMnemonic,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
            )
        }
    }
}