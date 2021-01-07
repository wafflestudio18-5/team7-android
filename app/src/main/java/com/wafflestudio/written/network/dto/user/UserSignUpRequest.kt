package com.wafflestudio.written.network.dto.user

import com.squareup.moshi.Json

data class UserSignUpRequest(
    val facebookid: String,
    val access_token: String,
//    @field:Json(name = "access_token")
//    val accessToken: String,
    val nickname: String
)
