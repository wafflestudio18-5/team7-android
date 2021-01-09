package com.wafflestudio.written.ui.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.repository.UserRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class SettingsViewModel @ViewModelInject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val confirmLogoutSubject = BehaviorSubject.createDefault(false)

    fun observeConfirmLogout(): Observable<Boolean> = confirmLogoutSubject.hide()

    fun confirmLogout() {
        confirmLogoutSubject.onNext(true)
    }

    fun getUser() = userRepository.getUserMe()



}