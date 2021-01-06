package com.wafflestudio.written.ui.main.my

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.dto.user.UserGetPostingsByIdResponse
import com.wafflestudio.written.network.service.UserService
import com.wafflestudio.written.repository.UserRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class MyViewModel @ViewModelInject constructor(
    private val userService: UserService,
    private val userRepository: UserRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var cursor: String? = null
    private var loadingPostings: Boolean = false
    private val postingsSubject = BehaviorSubject.create<List<PostingDto>>()
    fun getUser() = userRepository.getUserMe()

    fun observePostings(): Observable<List<PostingDto>> = postingsSubject.hide()

    fun getNextPostings() {
        if (!loadingPostings) {
            loadingPostings = true
            getUser().flatMap {
                userService.getPostingByUserId(it.id, cursor)
            }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    postingsSubject.onNext(postingsSubject.value.plus(it.postings))
                    cursor = if (it.hasNext) it.cursor else null
                    loadingPostings = false
                }, {
                    Timber.d(it)
                })
                .also { compositeDisposable.add(it) }
        }
    }

    fun getMyPostings(
        cursor: String?
    ): Single<UserGetPostingsByIdResponse> {
        return getUser().flatMap {
            userService.getPostingByUserId(userId = it.id, cursor = cursor)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
