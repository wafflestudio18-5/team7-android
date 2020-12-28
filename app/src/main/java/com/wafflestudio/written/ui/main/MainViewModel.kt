package com.wafflestudio.written.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.network.service.TempService

class MainViewModel @ViewModelInject constructor(private val service: TempService): ViewModel() {



}