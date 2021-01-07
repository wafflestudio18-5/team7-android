package com.wafflestudio.written.ui.writer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.models.UserDto
import com.wafflestudio.written.network.service.PostingService
import com.wafflestudio.written.network.service.UserService
import com.wafflestudio.written.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class WriterViewModel @ViewModelInject constructor(
    private val postingService: PostingService,
    private val userService: UserService,
    private val userRepository: UserRepository
): ViewModel() {

    private var cursor: String? = null
    private var hasNext: Boolean = false
    private var loadingPostings: Boolean = false
    var writerId: Int = -1

    private var postingsSubject = BehaviorSubject.create<List<PostingDto>>()
    private val compositeDisposable = CompositeDisposable()

    fun getWriter() = userService.getUserById(userId = writerId)

    fun getWriterPostings(writerId: Int) {
        loadingPostings = true
        userService.getPostingByUserId(writerId, cursor)
            .flatMap {
                loadingPostings = false
                postingsSubject.onNext(postingsSubject.value.plus(it.postings))
                hasNext = it.hasNext
                cursor = if (it.hasNext) it.cursor else null
                Single.just(it)
            }
    }

    fun getNextPostings(writerId: Int) {
        if(!loadingPostings and hasNext) {
            loadingPostings = true
            userService.getPostingByUserId(writerId, cursor)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    loadingPostings = false
                    postingsSubject.onNext(postingsSubject.value.plus(it.postings))
                    hasNext = it.hasNext
                    cursor = if (it.hasNext) it.cursor else null
                }, {
                    loadingPostings = false
                    Timber.d(it)
                })
                .also { compositeDisposable.add(it) }
        }
    }


}
