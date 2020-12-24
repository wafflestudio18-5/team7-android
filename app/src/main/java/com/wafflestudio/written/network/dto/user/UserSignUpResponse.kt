package com.wafflestudio.written.network.dto.user

import com.wafflestudio.written.models.UserDto

data class UserSignUpResponse(
    val user: UserDto,
    val access_token: String
)
