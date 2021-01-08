package com.wafflestudio.written.ui.main.saved

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.dto.posting.PostingScrappedResponse
import com.wafflestudio.written.network.service.PostingService
import com.wafflestudio.written.repository.UserRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class SavedViewModel @ViewModelInject constructor(
    private val postingService: PostingService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var cursor: String? = null
    private var hasNext: Boolean = false
    private var loadingPostings: Boolean = false
    private val postingsSubject = BehaviorSubject.createDefault<List<PostingDto>>(emptyList())

    fun observePostings(): Observable<List<PostingDto>> = postingsSubject.hide()

    fun getNextPostings() {
        if(!loadingPostings && hasNext) {
            loadingPostings = true
            postingService.getScrappedPostings(cursor)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    postingsSubject.onNext(postingsSubject.value.plus(it.storedPostings?: emptyList()))
                    hasNext = it.hasNext
                    cursor = if (it.hasNext) it.cursor else null
                    loadingPostings = false
                }, {
                    loadingPostings = false
                    Timber.d(it)
                })
                .also { compositeDisposable.add(it) }
        }
    }

    fun getSavedPostings(): Single<PostingScrappedResponse> {
        loadingPostings = true
        return postingService.getScrappedPostings(null)
            .flatMap {
                hasNext = it.hasNext
                loadingPostings = false
                postingsSubject.onNext(postingsSubject.value.plus(it.storedPostings?: emptyList()))
                cursor = if (it.hasNext) it.cursor else null
                Single.just(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
