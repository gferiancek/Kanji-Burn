package com.gavinferiancek.ui_reviewDetail.ui

sealed class ReviewDetailEvents {

    data class GetReviewById(
        val id: Int,
        val apiKey: String,
    ): ReviewDetailEvents()
}
