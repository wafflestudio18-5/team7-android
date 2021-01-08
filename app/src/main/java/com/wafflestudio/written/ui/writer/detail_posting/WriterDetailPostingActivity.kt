package com.wafflestudio.written.ui.writer.detail_posting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivityWriterDetailPostingBinding
import com.wafflestudio.written.extension.StringExtension
import com.wafflestudio.written.models.PostingDto
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_writer_detail_posting.*
import kotlinx.android.synthetic.main.writer_bottom_app_bar_detail_posting.view.*
import timber.log.Timber

@AndroidEntryPoint
class WriterDetailPostingActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, WriterDetailPostingActivity::class.java)
        }
    }

    private val viewModel: WriterDetailPostingViewModel by viewModels()
    private lateinit var binding: ActivityWriterDetailPostingBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriterDetailPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.observePosting().subscribe {
            modelView(it)
        }

        val intent = intent
        val posting: PostingDto = intent.getParcelableExtra("posting")?: run {
            finish()
            return
        }
        viewModel.setPosting(posting)

        viewModel.getPostingDetail(posting.id)

        binding.bottomAppBar.backText.setOnClickListener {
            finish()
        }

        binding.bottomAppBar.writerPostingText.setOnClickListener {
            finish()
        }

        binding.bottomAppBar.scrapText.setOnClickListener {
            viewModel.scrapPosting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, "글을 담아갔습니다.", Toast.LENGTH_SHORT).show()
                }, {
                    Timber.d(it)
                })
                .also { compositeDisposable.add(it) }
        }
    }

    private fun modelView(posting: PostingDto) {
        binding.bottomAppBar.writerText.text = posting.writer.nickname
        binding.titleText.text = posting.title
        binding.contentText.text = posting.content
        binding.writerText.text = posting.writer.nickname
        binding.createdAtText.text = StringExtension().modifyCreatedAt(posting.createdAt)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
