package com.wafflestudio.written.network.dto.user

import com.squareup.moshi.Json
import com.wafflestudio.written.models.UserDto

data class UserLoginResponse(
    val user: UserDto,
    val access_token: String
//    @field:Json(name = "access_token")
//    val accessToken: String
)
