package com.gavinferiancek.core_ui.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.gavinferiancek.core_ui.theme.kanji
import com.gavinferiancek.core_ui.theme.radical
import com.gavinferiancek.core_ui.theme.vocab

/**
 * Util fun that will take a string with various markup tags and remove the tags and apply correct styling
 * to the text contained between it based on tag type. All possible tag types:
 * <ja></ja> - Contains plain Japanese text
 * <I></I> - No clear purpose (just normal text on website). Just remove tags
 * <a href></a> - Contains url info and hyperlink text
 * <radical></radical> - References Radical. Use Radical colors in annotation.
 * <kanji></kanji> - References Kanji character. Use Kanji colors in annotation.
 * <vocabulary></vocabulary> - References Vocab word. Use Vocab colors in annotation.
 * <reading></reading> - Annotate text to show helpful pronunciation information.
 */
@Composable
fun generateAnnotatedString(
    sourceText: String,
): AnnotatedString {
    // Example text: "Hello <kanji>One</kanji> nice weather today!" would result in splitString =
    // [Hello, kanji, one, /kanji, nice weather today!] which leaves us with five parts:
    // i: Regular text, no annotations required.
    // i+1: Type of annotation to apply
    // i+2: Text we wish to annotate
    // i+3: Closing tag, we skip over this
    // i+4: Rest of the string. By stepping by 4, this is i in the next pass.
    val splitString = sourceText.split("<", ">")
    val annotatedString = buildAnnotatedString {
        for (i in 0..splitString.lastIndex step 4) {
            append(splitString[i])
            if (i != splitString.lastIndex) {
                val markupTag = splitString[i + 1]
                val targetText = splitString[i + 2]
                when {
                    markupTag.contains("a href") -> {
                        val hyperlinkData = createHyperlink(
                            sourceText = sourceText,
                            markupTag = markupTag,
                            hyperlinkText = targetText
                        )
                        withStyle(
                            style = SpanStyle(
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(targetText)
                        }
                        addStringAnnotation(
                            tag = "URL",
                            annotation = hyperlinkData.first,
                            start = hyperlinkData.second,
                            end = hyperlinkData.third,
                        )
                    }
                    markupTag == "ja" || markupTag == "I" -> append(targetText)
                    else -> {
                        AppendSpan(
                            builder = this,
                            markupTag = markupTag,
                            text = targetText,
                        )
                    }
                }
            }
        }
    }
    return annotatedString
}

/**
 * Extracts URL from an a href tag and calculates the start and end indices of annotated text
 * for the final string.  Returns a Triple in the format of <url, startIndex, endIndex>
 */
private fun createHyperlink(
    sourceText: String,
    markupTag: String,
    hyperlinkText: String,
): Triple<String, Int, Int> {
    // <a href> tag would look like <a href=\"www.example.com\"> so by splitting at double quotes we have
    // the url as the 2nd item. (
    val url = markupTag.split("\"")[1]

    // We need to take sourceText and strip out all of the markup tags and follow two spacing rules:
    // 1) Any tag that we will draw a background for we add "padding" with an extra character at the
    // start/end of the string. So for those tags (all but <a href> and <ja>) we want to replace the tag with a space.
    // 2) <a href> and <ja> tags don't require the padded characters, so the tags are removed.
    val strippedString = sourceText
        .replace("<a.*?>|</a>|<ja>|</ja>".toRegex(), "")
        .replace("<.*?>".toRegex(), " ")

    val startIndex = strippedString.indexOf(hyperlinkText)
    val endIndex = startIndex + hyperlinkText.length
    return Triple(url, startIndex, endIndex)
}

/**
 * Draws background around text depending on the supplied markupTag. To address readability concerns,
 * we need start/end padding. Using whitespace, i.e append(" $text "), is the easiest solution but it
 * leads to the following two problems:
 *
 * 1) When laying out text, if a word is the first OR last in the line, the Text Composable will remove
 * any leading/trailing whitespace, making it so only one side has "padding".
 *
 * 2) If the text is more than one word long, composable does not know to group those words together
 * and shift them all to the next line if they all do not fit. This means your phrase will be split among
 * two lines and also experience issue #1 since it will both be the last and first word on a line.
 *
 * The only way I could find to solve BOTH of these issues is to replace all spaces with a "-" whose
 * text color is the same as the background color. It is not pretty code... but it works. If I can find
 * a way to solve issue #1 I can simply use whitespace.
 */
@Composable
private fun AppendSpan(
    builder: AnnotatedString.Builder,
    markupTag: String,
    text: String,
) {
    val textSpanStyle = SpanStyle(
        color = MaterialTheme.colors.onPrimary,
        background = when (markupTag) {
            "radical" -> MaterialTheme.colors.radical
            "kanji" -> MaterialTheme.colors.kanji
            "vocabulary" -> MaterialTheme.colors.vocab
            "reading" -> Color.DarkGray
            else -> Color.Unspecified
        }
    )
    val paddingSpanStyle = textSpanStyle.copy(color = textSpanStyle.background)

    builder.apply {
        val words = text.split(" ")
        words.forEach { word ->
            withStyle(paddingSpanStyle) { append("-") }
            withStyle(textSpanStyle) { append(word) }
            withStyle(paddingSpanStyle) { append("-") }
            if (word != words.last()) append(" ")
        }
    }
}