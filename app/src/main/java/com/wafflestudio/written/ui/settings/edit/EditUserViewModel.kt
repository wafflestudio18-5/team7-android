package com.wafflestudio.written.ui.settings.edit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.network.service.UserService
import com.wafflestudio.written.repository.UserRepository

class EditUserViewModel @ViewModelInject constructor(
    private val userService: UserService,
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUser() = userRepository.getUserMe()

    fun resetuser() = userRepository.resetUser()

    fun updateUser(
        nickname: String,
        description: String
    ) = userService.updateUserInfo(nickname, description)


}