package com.wafflestudio.written.ui.writer

import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.models.UserDto
import com.wafflestudio.written.network.service.PostingService
import com.wafflestudio.written.network.service.UserService
import com.wafflestudio.written.repository.UserRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class WriterViewModel @ViewModelInject constructor(
    private val userService: UserService
): ViewModel() {

    private var cursor: String? = null
    private var hasNext: Boolean = false
    private var loadingPostings: Boolean = false
    var writerId: Int = -1
    var isSubscribingSubject = BehaviorSubject.createDefault(false)

    private var postingsSubject = BehaviorSubject.createDefault<List<PostingDto>>(emptyList())
    private val compositeDisposable = CompositeDisposable()

    fun observePostings(): Observable<List<PostingDto>> = postingsSubject.hide()
    fun observeSubscribing(): Observable<Boolean> = isSubscribingSubject.hide()

    fun getWriter() = userService.getUserById(userId = writerId).doOnSuccess { isSubscribingSubject.onNext(it.subscribing?:false) }

    fun getWriterPostings() {
        loadingPostings = true
        userService.getPostingByUserId(writerId, cursor)
            .subscribeOn(Schedulers.io())
            .subscribe({
                loadingPostings = false
                postingsSubject.onNext(postingsSubject.value.plus(it.postings?: emptyList()))
                hasNext = it.hasNext
                cursor = if (it.hasNext) it.cursor else null
            }, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }
    }

    fun getNextPostings() {
        if(!loadingPostings && hasNext) {
            loadingPostings = true
            userService.getPostingByUserId(writerId, cursor)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    loadingPostings = false
                    postingsSubject.onNext(postingsSubject.value.plus(it.postings?: emptyList()))
                    hasNext = it.hasNext
                    cursor = if (it.hasNext) it.cursor else null
                }, {
                    loadingPostings = false
                    Timber.d(it)
                })
                .also { compositeDisposable.add(it) }
        }
    }

    fun changeSubscribing() {
        if(isSubscribingSubject.value) {
            userService.unsubscribeUserById(writerId)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    isSubscribingSubject.onNext(false)
                }, {
                    Timber.d(it)
                })
                .also { compositeDisposable.add(it) }
        }
        else {
            userService.subscribeUserById(writerId)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    isSubscribingSubject.onNext(true)
                }, {
                    Timber.d(it)
                })
                .also { compositeDisposable.add(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
