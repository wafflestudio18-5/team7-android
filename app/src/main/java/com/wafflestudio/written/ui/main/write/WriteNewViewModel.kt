package com.wafflestudio.written.ui.main.write

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class WriteNewViewModel: ViewModel() {

    private val expandSubject = BehaviorSubject.createDefault(false)

    fun observeExpand(): Observable<Boolean> = expandSubject.hide()

    fun expand() = expandSubject.onNext(true)

    fun close() = expandSubject.onNext(false)

}