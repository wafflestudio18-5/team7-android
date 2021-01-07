package com.wafflestudio.written.ui.main.subscribe.detail_posting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivitySubscribeDetailPostingBinding
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.ui.writer.WriterActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_subscribe_detail_posting.*
import kotlinx.android.synthetic.main.subscribe_bottom_app_bar_detail_posting.view.*
import timber.log.Timber

@AndroidEntryPoint
class SubscribeDetailPostingActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, SubscribeDetailPostingActivity::class.java)
        }
    }

    private val viewModel: SubscribeDetailPostingViewModel by viewModels()
    private lateinit var binding: ActivitySubscribeDetailPostingBinding

    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscribeDetailPostingBinding.inflate(layoutInflater)
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
            val intent = WriterActivity.createIntent(this)
            intent.putExtra("writerId", viewModel.writerId)
            startActivity(intent)
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
        binding.contentText.gravity = when(posting.alignment) {
            "LEFT" -> Gravity.LEFT
            "CENTER" -> Gravity.CENTER
            else -> Gravity.CENTER
        }
        binding.writerText.text = posting.writer.nickname
        binding.createdAtText.text = posting.createdAt
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
