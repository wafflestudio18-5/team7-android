package com.wafflestudio.written.ui.writer.detail_posting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.service.PostingService

class WriterDetailPostingViewModel @ViewModelInject constructor(private val postingService: PostingService) : ViewModel() {

    lateinit var posting: PostingDto

    fun getPostingDetail(postingId: Int) = postingService.getPostingById(postingId.toLong())

}