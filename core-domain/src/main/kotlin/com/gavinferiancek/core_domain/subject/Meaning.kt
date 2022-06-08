package com.gavinferiancek.core_domain.subject

data class Meaning(
    val meaning: String,
    val primary: Boolean,
    val acceptedAnswer: Boolean
)

fun List<Meaning>.getPrimaryMeaning(): String {
    return first { it.primary }.meaning
}

/**
 * Meanings are stored as a list of Meaning objects and needs to be built into a string before being
 * displayed to the UI. We provide a helper method to find all non-primary meanings and add them to
 * a single, properly formatted string.
 */
fun List<Meaning>.getAlternateMeanings(): String {
    val alternateMeaningString = StringBuilder()
    val alternateMeanings = filter { !it.primary }
    alternateMeanings.forEachIndexed { index, meaning ->
        if (index == alternateMeanings.count() - 1) {
            alternateMeaningString.append(meaning.meaning)
        } else {
            alternateMeaningString.append("${meaning.meaning}, ")
        }
    }
    return alternateMeaningString.toString()
}