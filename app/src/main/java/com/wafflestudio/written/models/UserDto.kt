package com.wafflestudio.written.models

data class UserDto(
    val id: Int,
    val nickname: String,
    val description: String,
    val first_posted_at: String
)