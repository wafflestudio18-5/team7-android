package com.wafflestudio.written.network.dto.posting

import com.wafflestudio.written.models.PostingDto

data class PostingTitleResponse (
    val id: String,
    val name: String,
    val postings: List<PostingDto>
)