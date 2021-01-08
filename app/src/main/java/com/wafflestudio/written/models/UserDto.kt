package com.wafflestudio.written.models

import com.squareup.moshi.Json

data class UserDto(
    val id: Int,
    val nickname: String,
    val description: String,
    @Json(name = "first_posted_at")
    val firstPostedAt: String?,
    @Json(name = "count_public_postings")
    val countPublicPostings: Int?,
    @Json(name = "count_all_postings")
    val countAllPostings: Int?,
    val subscribing: Boolean?
)
