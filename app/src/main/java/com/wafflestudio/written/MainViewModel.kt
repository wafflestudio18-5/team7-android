package com.wafflestudio.written

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.network.TempService
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(private val service: TempService): ViewModel() {

}