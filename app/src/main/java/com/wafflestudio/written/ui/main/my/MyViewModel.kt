package com.wafflestudio.written.ui.main.my

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.UserDto
import com.wafflestudio.written.network.service.PostingService
import com.wafflestudio.written.network.service.UserService

class MyViewModel @ViewModelInject constructor(private val userService: UserService): ViewModel() {

    lateinit var user: UserDto
    var hasNextPosting: Boolean = false
    var cursor: String? = null

    fun getUser() = userService.getUserMe()

    fun getMyPostings(
        userId: Int,
        cursor: String?,
        pageSize: Int
    ) = userService.getPostingByUserId(userId = userId, cursor = cursor, pageSize = pageSize)
    
}
