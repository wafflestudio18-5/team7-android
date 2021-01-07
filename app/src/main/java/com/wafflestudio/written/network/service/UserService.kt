package com.wafflestudio.written.network.service

import com.wafflestudio.written.network.dto.user.UserLoginRequest
import com.wafflestudio.written.network.dto.user.UserSignUpRequest
import com.wafflestudio.written.network.dto.user.UserUpdateRequest
import com.wafflestudio.written.network.service.retrofit.UserRetrofitService

class UserService(
    private val userRetrofitService: UserRetrofitService
) {

    // User sign up
    fun signUpUser(
        facebookid: String,
        accessToken: String,
        nickname: String
    ) = userRetrofitService.signUpUser(UserSignUpRequest(facebookid, accessToken, nickname))

    // User login
    fun loginUser(
        facebookid: String,
        accessToken: String
    ) = userRetrofitService.loginUser(UserLoginRequest(facebookid, accessToken))

    // User get me
    fun getUserMe() = userRetrofitService.getUserMe()

    // User get {user_id}
    fun getUserById(userId: Int) = userRetrofitService.getUserById(userId.toString())

    // User update personal data
    fun updateUserInfo(
        nickname: String,
        description: String
    ) = userRetrofitService.updateUserInfo(UserUpdateRequest(nickname, description))

    fun getPostingByUserId(
        userId: Int,
        cursor: String?,
        pageSize: Int = 20
    ) = userRetrofitService.getPostingsByUserId(
        userId.toString(),
        cursor,
        pageSize.toString()
    )


}
