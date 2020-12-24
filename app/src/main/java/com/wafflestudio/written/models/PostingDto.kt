package com.wafflestudio.written.models

data class PostingDto(
    val writer: PostingDtoWriter,
    val id: Int,
    val title: String,
    val content: String,
    val created_at: String
)

data class PostingDtoWriter(
    val id: Int,
    val nickname: String
)
