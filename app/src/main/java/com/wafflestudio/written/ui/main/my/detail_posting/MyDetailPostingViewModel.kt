package com.wafflestudio.written.ui.main.my.detail_posting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.service.PostingService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class MyDetailPostingViewModel @ViewModelInject constructor(private val postingService: PostingService) : ViewModel() {

    private val postingSubject = BehaviorSubject.create<PostingDto>()
    private val compositeDisposable = CompositeDisposable()
    private val confirmDeleteSubject = BehaviorSubject.createDefault(false)

    fun observePosting(): Observable<PostingDto> = postingSubject.hide()
    fun observeConfirmDelete(): Observable<Boolean> = confirmDeleteSubject.hide()

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
        postingSubject.onNext(posting)
    }

    fun checkPublic() = postingSubject.value.isPublic

    fun changePublic() {
        postingService.updatePosting(
            postingId = postingSubject.value.id.toLong(),
            content = postingSubject.value.content,
            alignment = postingSubject.value.alignment,
            isPublic = !postingSubject.value.isPublic
        )
            .subscribeOn(Schedulers.io())
            .subscribe({
                setPosting(it)
            }, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }
    }

    fun deletePosting() = postingService.deletePosting(postingSubject.value.id.toLong())

    fun confirmDelete() {
        confirmDeleteSubject.onNext(true)
    }
    fun cancelDelete() = confirmDeleteSubject.onNext(false)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
