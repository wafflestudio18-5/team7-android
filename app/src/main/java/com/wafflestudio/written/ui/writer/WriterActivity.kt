package com.wafflestudio.written.ui.writer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivityWriterBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_writer.*
import timber.log.Timber

@AndroidEntryPoint
class WriterActivity : AppCompatActivity() {

    private val writerViewModel: WriterViewModel by viewModels()
    private lateinit var binding: ActivityWriterBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        writerViewModel.writerId = intent.getIntExtra("writerId", -1)

        writerViewModel.getWriter()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                nickname_text.text = it.nickname
                description_text.text = it.description
            }, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }



    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}