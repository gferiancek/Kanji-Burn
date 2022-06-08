package com.gavinferiancek.core_domain.reviewstatistics

data class ReviewStatistics(
    val id:Int,
    val meaningCorrect: Int,
    val meaningIncorrect: Int,
    val meaningMaxStreak: Int,
    val meaningCurrentStreak: Int,
    val readingCorrect: Int,
    val readingIncorrect: Int,
    val readingMaxStreak: Int,
    val readingCurrentStreak: Int,
    val percentageCorrect: Int,
    val hidden: Boolean,
) {
    fun getTotalCorrectPercentage() = (getTotalAttempts()).toFloat() / (meaningCorrect + readingCorrect).toFloat()

    fun getMeaningCorrectPercentage() = (meaningCorrect.toFloat() + meaningIncorrect.toFloat()) / meaningCorrect.toFloat()

    fun getReadingCorrectPercentage() = (readingCorrect.toFloat() + readingIncorrect.toFloat()) / readingCorrect.toFloat()

    fun getTotalAttempts() = meaningCorrect + meaningIncorrect + readingCorrect + readingIncorrect

    fun getMeaningAttempts() = meaningCorrect + meaningIncorrect

    fun getReadingAttempts() = readingCorrect + readingIncorrect
}
