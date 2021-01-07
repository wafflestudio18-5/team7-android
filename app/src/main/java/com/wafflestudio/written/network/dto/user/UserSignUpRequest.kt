package com.wafflestudio.written.network.dto.user

data class UserSignUpRequest(
    val facebookid: String,
    val access_token: String,
    val nickname: String
)
