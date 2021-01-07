package com.wafflestudio.written.ui.writer.detail_posting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivityWriterDetailPostingBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_writer_detail_posting.*
import kotlinx.android.synthetic.main.writer_bottom_app_bar_detail_posting.view.*
import timber.log.Timber

class WriterDetailPostingActivity : AppCompatActivity() {

    private val viewModel: WriterDetailPostingViewModel by viewModels()
    private lateinit var binding: ActivityWriterDetailPostingBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriterDetailPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        viewModel.posting = intent.getParcelableExtra("posting")?: run {
            finish()
            return
        }

        binding.bottomAppBar.writerText.text = viewModel.posting.writer.nickname
        binding.titleText.text = viewModel.posting.title
        binding.contentText.text = viewModel.posting.content
        binding.writerText.text = viewModel.posting.writer.nickname
        binding.createdAtText.text = viewModel.posting.createdAt

        viewModel.getPostingDetail(viewModel.posting.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModel.posting = it
                binding.titleText.text = viewModel.posting.title
                binding.contentText.text = viewModel.posting.content
                binding.writerText.text = viewModel.posting.writer.nickname
                binding.createdAtText.text = viewModel.posting.createdAt
            }, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }

        bottom_app_bar.back_text.setOnClickListener {
            finish()
        }

        bottom_app_bar.writer_posting_text.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
