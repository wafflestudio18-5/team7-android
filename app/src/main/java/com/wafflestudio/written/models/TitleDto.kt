package com.wafflestudio.written.models

import com.squareup.moshi.Json

data class TitleDto (
    val id: String,
    val name: String,
    @Json(name = "count_public_postings")
    val countPublicPostings: Int,
    @Json(name = "count_all_postings")
    val countAllPostings: Int
)
