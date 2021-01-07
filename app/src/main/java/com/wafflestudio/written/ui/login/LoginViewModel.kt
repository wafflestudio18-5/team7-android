package com.wafflestudio.written.ui.login

import androidx.core.content.edit
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.wafflestudio.written.App
import com.wafflestudio.written.network.service.UserService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel @ViewModelInject constructor(private val service: UserService): ViewModel() {

    fun login(facebookId: String, accessToken: String): Completable =
        service.loginUser(facebookId, accessToken)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                PreferenceManager.getDefaultSharedPreferences(App.APP)
                    .edit {
                        putString("ACCESS_TOKEN", it.accessToken)
                    }
            }
            .ignoreElement()

}
