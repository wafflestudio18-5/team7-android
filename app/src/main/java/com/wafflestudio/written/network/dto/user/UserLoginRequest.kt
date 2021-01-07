package com.wafflestudio.written.network.dto.user

import com.squareup.moshi.Json

data class UserLoginRequest(
    val facebookid: String,
    @Json(name = "access_token")
    val accessToken: String
)
