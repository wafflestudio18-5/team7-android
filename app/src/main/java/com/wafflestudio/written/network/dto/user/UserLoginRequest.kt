package com.wafflestudio.written.network.dto.user

import com.squareup.moshi.Json

data class UserLoginRequest(
    val facebookid: String,
    val access_token: String
//    @field:Json(name = "access_token")
//    val accessToken: String
)
