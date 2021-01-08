package com.wafflestudio.written.ui.main.write

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.network.dto.posting.PostingTodayResponse
import com.wafflestudio.written.network.service.PostingService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class WriteHomeViewModel @ViewModelInject constructor(private val service: PostingService) : ViewModel(){

    private val titleSubject = BehaviorSubject.createDefault("")

    fun observeTitle(): Observable<String> = titleSubject.hide()

    fun getTitleToday(): Single<PostingTodayResponse> =
        service.getPostingToday()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                titleSubject.onNext(it.title)
                Timber.d(it.title)
            }

}