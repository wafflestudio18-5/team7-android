package com.wafflestudio.written.network.dto.user

import com.wafflestudio.written.models.UserDto

data class UserLoginResponse(
    val user: UserDto,
    val access_token: String
)
