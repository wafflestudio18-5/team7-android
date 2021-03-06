package com.wafflestudio.written.network.dto.posting

import com.squareup.moshi.Json
import com.wafflestudio.written.models.PostingDto

data class PostingScrappedResponse(
    @Json(name = "stored_postings")
    val storedPostings: List<PostingDto>?,
    @Json(name = "has_next")
    val hasNext: Boolean,
    val cursor: String?
)
