package com.wafflestudio.written.ui.writer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.databinding.ActivityWriterBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class WriterActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, WriterActivity::class.java)
        }
    }

    private val writerViewModel: WriterViewModel by viewModels()
    private lateinit var binding: ActivityWriterBinding

    private lateinit var writerPostingAdapter: WriterPostingAdapter
    private lateinit var writerLayoutManager: LinearLayoutManager

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        writerViewModel.writerId = intent.getIntExtra("writerId", -1)

        writerPostingAdapter = WriterPostingAdapter(this)
        writerLayoutManager = LinearLayoutManager(this)
        binding.myPostingsRecyclerview.adapter = writerPostingAdapter
        binding.myPostingsRecyclerview.layoutManager = writerLayoutManager

        writerViewModel.observePostings()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                writerPostingAdapter.postings = it
                writerPostingAdapter.notifyDataSetChanged()
            }

        writerViewModel.getWriter()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                binding.nicknameText.text = it.nickname
                binding.descriptionText.text = it.description
            }, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }

        writerViewModel.getWriterPostings()

        binding.myPostingsRecyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalPostings = writerPostingAdapter.itemCount
                val lastVisiblePostingPosition =
                    writerLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePostingPosition >= totalPostings - 1) {
                    writerViewModel.getNextPostings()
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
