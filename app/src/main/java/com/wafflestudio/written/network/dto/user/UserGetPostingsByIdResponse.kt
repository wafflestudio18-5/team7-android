package com.wafflestudio.written.network.dto.user

import com.squareup.moshi.Json
import com.wafflestudio.written.models.PostingDto

data class UserGetPostingsByIdResponse (
    val postings: List<PostingDto>?,
    @Json(name = "has_next")
    val hasNext: Boolean,
    val cursor: String?
)
