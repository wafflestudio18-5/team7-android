package com.wafflestudio.written.network.dto.posting

import com.squareup.moshi.Json

data class PostingUpdateRequest(
    val content: String,
    val alignment: String,
    @Json(name = "is_public")
    val isPublic: String
)
