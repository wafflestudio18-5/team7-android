package com.wafflestudio.written.network.service.retrofit


import com.wafflestudio.written.models.UserDto
import com.wafflestudio.written.network.dto.user.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface UserRetrofitService {

    // User sign up
    @POST("users/")
    fun signUpUser(
        @Body Body: UserSignUpRequest
    ): Single<UserSignUpResponse>

    // User login
    @PUT("users/login/")
    fun loginUser(
        @Body Body: UserLoginRequest
    ): Single<UserLoginResponse>

    // User get me
    @GET("users/me/")
    fun getUserMe(): Single<UserDto>

    // User get {user_id}
    @GET("users/{user_id}/")
    fun getUserById(
        @Path("user_id") userId: String
    ): Single<UserDto>

    // User update personal data
    @PUT("users/me/")
    fun updateUserInfo(
        @Body body: UserUpdateRequest
    ): Single<UserDto>

    // User get {user_id} Posting
    @GET("users/{user_id}/postings/")
    fun getPostingsByUserId(
        @Path("user_id") userId: String,
        @Query("cursor") cursor: String?,
        @Query("page_size") pageSize: String
    ): Single<UserGetPostingsByIdResponse>

    @GET("users/subscribed/")
    fun getSubscribingUsers(
        @Query("cursor") cursor: String?,
        @Query("page_size") pageSize: String
    ): Single<UserGetSubscribingResponse>

    @GET("users/subscriber/")
    fun getSubscribedUsers(
        @Query("cursor") cursor: String?,
        @Query("page_size") pageSize: String
    ): Single<UserGetSubscribingResponse>

    @POST("users/{user_id}/subscribe/")
    fun subscribeUserById(
        @Path("user_id") userId: String
    ): Completable

    @POST("users/{user_id}/unsubscribe/")
    fun unsubscribeUserById(
        @Path("user_id") userId: String
    ): Completable
}
