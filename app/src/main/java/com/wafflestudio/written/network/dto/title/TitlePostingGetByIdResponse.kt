package com.wafflestudio.written.network.dto.title

import com.squareup.moshi.Json
import com.wafflestudio.written.models.PostingDto

data class TitlePostingGetByIdResponse(
    val postings: List<PostingDto>,
    @Json(name = "has_next")
    val hasNext: Boolean,
    val cursor: String?
)
