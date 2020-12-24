package com.wafflestudio.written.network.dto.posting

data class PostingWriteRequest(
    val title: String,
    val content: String,
    val alignment: String
)