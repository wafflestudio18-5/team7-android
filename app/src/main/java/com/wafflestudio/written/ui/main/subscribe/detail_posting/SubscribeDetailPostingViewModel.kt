package com.wafflestudio.written.ui.main.subscribe.detail_posting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.network.service.PostingService

class SubscribeDetailPostingViewModel @ViewModelInject constructor(private val postingService: PostingService): ViewModel() {



    fun getPostingDetail(postingId: Long) = postingService.getPostingById(postingId)

}