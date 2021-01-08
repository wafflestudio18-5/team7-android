package com.wafflestudio.written.ui.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.repository.UserRepository

class SettingsViewModel @ViewModelInject constructor(private val userRepository: UserRepository) : ViewModel() {


    fun getUser() = userRepository.getUserMe()

}