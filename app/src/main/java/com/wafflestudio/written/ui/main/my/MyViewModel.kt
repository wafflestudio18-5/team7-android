package com.wafflestudio.written.ui.main.my

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.network.service.PostingService
import com.wafflestudio.written.network.service.UserService

class MyViewModel @ViewModelInject constructor(private val userService: UserService): ViewModel() {

    fun getUser() = userService.getUserMe()
    
}