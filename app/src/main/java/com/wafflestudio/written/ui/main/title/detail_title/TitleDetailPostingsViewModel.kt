package com.wafflestudio.written.ui.main.title.detail_title

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.service.TitleService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class TitleDetailPostingsViewModel @ViewModelInject constructor(private val titleService: TitleService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var cursor: String? = null
    private var hasNext: Boolean = false
    private var loadingPostings: Boolean = false
    private val postingsSubject = BehaviorSubject.createDefault<List<PostingDto>>(emptyList())
    private val titleSubject = BehaviorSubject.createDefault("")

    var titleId: Int = -1

    fun observePostings(): Observable<List<PostingDto>> = postingsSubject.hide()
    fun observeTitle(): Observable<String> = titleSubject.hide()

    fun getNextPostings() {
        if(!loadingPostings && hasNext) {
            loadingPostings = true
            titleService.getPostingsByTitleId(titleId.toLong(), cursor)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    postingsSubject.onNext(postingsSubject.value.plus(it.postings?: emptyList()))
                    titleSubject.onNext(it.title)
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

    fun getPostings() {
        loadingPostings = true
        titleService.getPostingsByTitleId(titleId.toLong(), null)
            .subscribeOn(Schedulers.io())
            .subscribe({
                postingsSubject.onNext(postingsSubject.value.plus(it.postings?: emptyList()))
                titleSubject.onNext(it.title)
                val title = it.title
                Timber.d("meep $title")
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
