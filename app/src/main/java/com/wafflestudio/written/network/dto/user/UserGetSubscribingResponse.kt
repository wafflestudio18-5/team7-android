package com.wafflestudio.written.network.dto.user

import com.squareup.moshi.Json
import com.wafflestudio.written.models.UserDto

data class UserGetSubscribingResponse (
    val writers: List<UserDto>,
    @Json(name = "has_next")
    val hasNext: Boolean,
    val cursor: String?
)
