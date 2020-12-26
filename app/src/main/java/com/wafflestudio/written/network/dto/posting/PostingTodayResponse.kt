package com.wafflestudio.written.network.dto.posting

import com.wafflestudio.written.models.PostingDto

data class PostingTodayResponse(
    val title: String,
    val postings: List<PostingDto>,
    val hasNext: Boolean,
    val cursor: String?
)
