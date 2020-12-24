package com.wafflestudio.written.models

import com.squareup.moshi.Json

data class PostingDto(
    val writer: PostingDtoWriter,
    val id: Int,
    val title: String,
    val content: String,
    @field:Json(name = "created_at")
    val createdAt: String
)

data class PostingDtoWriter(
    val id: Int,
    val nickname: String
)
