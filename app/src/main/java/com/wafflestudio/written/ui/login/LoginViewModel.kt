package com.wafflestudio.written.ui.login

import android.widget.Toast
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.App
import com.wafflestudio.written.network.dto.user.UserLoginRequest
import com.wafflestudio.written.network.dto.user.UserLoginResponse
import com.wafflestudio.written.network.service.UserService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel @ViewModelInject constructor(private val service: UserService): ViewModel() {

    fun login(facebookId: String, accessToken: String): Completable =
        service.loginUser(facebookId, accessToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                PreferenceManager.getDefaultSharedPreferences(App.APP)
                    .edit {
                        putString("ACCESS_TOKEN", it.accessToken)
                    }
            }
            .ignoreElement()

    fun signUp() {

    }

}