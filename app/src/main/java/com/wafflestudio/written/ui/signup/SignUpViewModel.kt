package com.wafflestudio.written.ui.signup

import android.preference.PreferenceManager
import androidx.core.content.edit
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.App
import com.wafflestudio.written.network.service.UserService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class SignUpViewModel @ViewModelInject constructor(private val service: UserService) : ViewModel() {

    fun signup(facebookid: String, access_token: String, nickname: String): Completable =
        service.signUpUser(facebookid, access_token, nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                PreferenceManager.getDefaultSharedPreferences(App.APP)
                    .edit {
                        putString("ACCESS_TOKEN", it.access_token)
                    }
            }
            .ignoreElement()

}