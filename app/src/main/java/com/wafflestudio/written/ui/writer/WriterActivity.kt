package com.wafflestudio.written.ui.writer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.databinding.ActivityWriterBinding
import com.wafflestudio.written.extension.StringExtension
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

        writerViewModel.observeSubscribing()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when(it) {
                    true -> {
                        binding.subscribeText.background = ContextCompat.getDrawable(this, R.drawable.layout_public_background)
                        binding.subscribeText.setTextColor(ContextCompat.getColor(this, R.color.white))
                    }
                    false -> {
                        binding.subscribeText.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
                        binding.subscribeText.setTextColor(ContextCompat.getColor(this, R.color.default_text_color))
                    }
                }
            }

        writerViewModel.getWriter()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                binding.nicknameText.text = it.nickname
                binding.descriptionText.text = it.description
                binding.publicManyText.text = it.countPublicPostings.toString() + "편 공개"
                binding.firstPostingText.text = if (it.firstPostedAt != null) StringExtension().modifyCreatedAt(it.firstPostedAt) + ", 첫 글." else "첫 글 없음."
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


        binding.backText.setOnClickListener {
            finish()
        }

        binding.subscribeText.setOnClickListener {
            writerViewModel.changeSubscribing()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
