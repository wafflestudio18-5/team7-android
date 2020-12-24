package com.wafflestudio.written.network.dto.user

import com.squareup.moshi.Json
import com.wafflestudio.written.models.UserDto

data class UserSignUpResponse(
    val user: UserDto,
    @field:Json(name = "access_token")
    val accessToken: String
)
