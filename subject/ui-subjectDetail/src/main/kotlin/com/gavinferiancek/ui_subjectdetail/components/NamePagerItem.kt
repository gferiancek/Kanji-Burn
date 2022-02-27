package com.gavinferiancek.ui_subjectdetail.components

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import javax.security.auth.Subject

@Composable
fun NamePagerItem(
    primaryMeaning: String,
    userSynonyms: String,
    meaningMnemonic: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(KanjiBurnTheme.colors.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row() {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            end = 16.dp,
                        )
                        .alignByBaseline(),
                    text = "PRIMARY",
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier
                        .alignByBaseline(),
                    text = primaryMeaning
                )
            }
            Row() {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            end = 16.dp,
                        )
                        .alignByBaseline(),
                    text = "USER SYNONYMS",
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier
                        .alignByBaseline(),
                    text = userSynonyms
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 16.dp),
                text = "Mnemonic",
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = meaningMnemonic,
                fontSize = 16.sp
            )
        }
    }
}