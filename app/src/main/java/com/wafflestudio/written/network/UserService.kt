package com.wafflestudio.written.network

import com.wafflestudio.written.models.UserDto
import com.wafflestudio.written.network.dto.user.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface UserService {

    // User sign up
    @POST("users")
    fun signUpUser(
        @Body Body: UserSignUpRequest
    ): Single<UserSignUpResponse>

    // User login
    @PUT("users/login")
    fun loginUser(
        @Body Body: UserLoginRequest
    ): Single<UserLoginResponse>

    // User get me
    @GET("users/me")
    fun getUserMe(): Single<UserDto>

    // User get {user_id}
    @GET("users/{user_id}")
    fun getUserById(
        @Path("user_id") user_id: String = ""
    ): Single<UserDto>

    // User update personal data
    @PUT("users/me")
    fun updateUserInfo(
        @Body Body: UserUpdateInfoRequest
    ): Single<UserDto>

    // User get {user_id} Posting
    @GET("/users/{user_id}/postings")
    fun getPostingsByUserId(
        @Query("cursor") cursor: Int = 0,
        @Query("page_size") page_size: Int = 5
    )

}