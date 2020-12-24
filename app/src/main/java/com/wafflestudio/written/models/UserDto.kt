package com.wafflestudio.written.models

import com.squareup.moshi.Json

data class UserDto(
    val id: Int,
    val nickname: String,
    val description: String,
    @field:Json(name = "first_posted_at")
    val firstPostedAt: String
)
