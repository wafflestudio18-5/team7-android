package com.wafflestudio.written.ui.signup

import androidx.core.content.edit
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.wafflestudio.written.App
import com.wafflestudio.written.network.service.UserService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class SignUpViewModel @ViewModelInject constructor(private val service: UserService) : ViewModel() {

    fun signup(facebookid: String, accessToken: String, nickname: String): Completable =
        service.signUpUser(facebookid, accessToken, nickname)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                PreferenceManager.getDefaultSharedPreferences(App.APP)
                    .edit {
                        putString("ACCESS_TOKEN", it.accessToken)
                    }
            }
            .ignoreElement()

}
