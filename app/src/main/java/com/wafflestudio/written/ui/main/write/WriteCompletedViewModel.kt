package com.wafflestudio.written.ui.main.write

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.network.dto.posting.PostingScrappedResponse
import com.wafflestudio.written.network.service.PostingService

class WriteCompletedViewModel @ViewModelInject constructor(private val service: PostingService): ViewModel() {



}