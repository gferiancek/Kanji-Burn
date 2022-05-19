package com.gavinferiancek.review_domain

sealed class DetailScreenItem(
    open val headerValue: String,
    open val contentValue: String,
) {
    data class ContextItem(
        override val contentValue: String = "Context",
    ) : DetailScreenItem(contentValue, contentValue)

    data class KanjiCompositionItem(
        override val headerValue: String = "Composition",
        override val contentValue: String = "Kanji Composition",
    ) : DetailScreenItem(headerValue, contentValue)

    data class KanjiUsageItem(
        override val headerValue: String = "Kanji Usage",
        override val contentValue: String = "Found In Kanji",
    ) : DetailScreenItem(headerValue, contentValue)

    data class MeaningItem(
        override val contentValue: String = "Meaning",
    ) : DetailScreenItem(contentValue, contentValue)

    data class NameItem(
        override val contentValue: String = "Name",
    ) : DetailScreenItem(contentValue, contentValue)

    data class RadicalCompositionItem(
        override val headerValue: String = "Composition",
        override val contentValue: String = "Radical Composition",
    ) : DetailScreenItem(headerValue, contentValue)

    data class ReadingItem(
        override val contentValue: String = "Reading",
    ) : DetailScreenItem(contentValue, contentValue)

    data class SimilarKanjiItem(
        override val headerValue: String = "Similar Kanji",
        override val contentValue: String = "Visually Similar Kanji",
    ) : DetailScreenItem(headerValue, contentValue)

    data class ProgressItem(
        override val headerValue: String = "Progress",
        override val contentValue: String = "Your Progression",
    ) : DetailScreenItem(headerValue, contentValue)

    data class VocabUsageItem(
        override val headerValue: String = "Vocab Usage",
        override val contentValue: String = "Found In Vocab",
    ) : DetailScreenItem(headerValue, contentValue)
}
