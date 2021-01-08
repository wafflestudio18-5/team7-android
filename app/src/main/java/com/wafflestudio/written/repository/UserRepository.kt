package com.wafflestudio.written.repository

import com.wafflestudio.written.models.UserDto
import com.wafflestudio.written.network.service.UserService
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class UserRepository (private val userService: UserService) {

    private var user: UserDto? = null

    fun getUserMe(): Single<UserDto> {
        return user?.let { Single.just(it) } ?: userService.getUserMe().doOnSuccess { user = it }
    }

    fun resetUser() {
        user = null
    }



}