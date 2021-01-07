package com.wafflestudio.written.network.dto.user

data class UserLoginRequest(
    val facebookid: String,
    val access_token: String
)
