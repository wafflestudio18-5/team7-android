package com.wafflestudio.written.ui.main.title

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.wafflestudio.written.models.TitleDto
import com.wafflestudio.written.network.service.TitleService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class TitleViewModel @ViewModelInject constructor(private val titleService: TitleService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var cursor: String? = null
    private var hasNext: Boolean = false
    private var loadingTitles: Boolean = false
    private val titlesSubject = BehaviorSubject.createDefault<List<TitleDto>>(emptyList())

    fun observeTitles(): Observable<List<TitleDto>> = titlesSubject.hide()

    fun getNextTitles() {
        if(!loadingTitles && hasNext) {
            loadingTitles = true
            titleService.getTitles(time = null, order = null, official = null, query = null, cursor = cursor)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    titlesSubject.onNext(titlesSubject.value.plus(it.titles?: emptyList()))
                    hasNext = it.hasNext
                    cursor = if (it.hasNext) it.cursor else null
                    loadingTitles = false
                }, {
                    loadingTitles = false
                    Timber.d(it)
                })
                .also { compositeDisposable.add(it) }
        }
    }

    fun getTitles() {
        loadingTitles = true
        titleService.getTitles(time = null, order = null, official = null, query = null, cursor = cursor)
            .subscribeOn(Schedulers.io())
            .subscribe({
                titlesSubject.onNext(titlesSubject.value.plus(it.titles ?: emptyList()))
                hasNext = it.hasNext
                cursor = if (it.hasNext) it.cursor else null
                loadingTitles = false
            }, {
                loadingTitles = false
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }
    }

}
