package com.wafflestudio.written.ui.main.write

import android.widget.GridLayout
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.service.PostingService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class WriteNewViewModel @ViewModelInject constructor(private val service: PostingService) : ViewModel() {

    private val expandSubject = BehaviorSubject.createDefault(false)

    fun observeExpand(): Observable<Boolean> = expandSubject.hide()

    fun expand() = expandSubject.onNext(true)

    fun close() = expandSubject.onNext(false)

    fun writePosting(title: String, content: String, alignment: String): Single<PostingDto> =
        service.writePosting(title, content, alignment)
            .subscribeOn(Schedulers.io())

    fun createTitle(name: String): Completable =
        service.createTitle(name)
            .subscribeOn(Schedulers.io())
            .ignoreElement()

}