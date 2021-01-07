package com.wafflestudio.written.ui.main.subscribe.detail_posting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.service.PostingService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class SubscribeDetailPostingViewModel @ViewModelInject constructor(private val postingService: PostingService): ViewModel() {

    private val postingSubject = BehaviorSubject.create<PostingDto>()
    private val compositeDisposable = CompositeDisposable()
    var writerId: Int = -1
    var postingId: Int = -1

    fun observePosting(): Observable<PostingDto> = postingSubject.hide()

    fun getPostingDetail(postingId: Int) {
        postingService.getPostingById(postingId.toLong())
            .subscribeOn(Schedulers.io())
            .subscribe({
                setPosting(it)
            }, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }
    }

    fun setPosting(posting: PostingDto) {
        writerId = posting.writer.id
        postingId = posting.id
        postingSubject.onNext(posting)
    }

    fun scrapPosting() = postingService.scrapPosting(postingId.toLong())

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
