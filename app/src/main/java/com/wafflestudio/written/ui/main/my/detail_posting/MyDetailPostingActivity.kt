package com.wafflestudio.written.ui.main.my.detail_posting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.wafflestudio.written.R
import com.wafflestudio.written.databinding.ActivityMyDetailPostingBinding
import com.wafflestudio.written.models.PostingDto
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class MyDetailPostingActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, MyDetailPostingActivity::class.java)
        }
    }

    private val viewModel: MyDetailPostingViewModel by viewModels()
    private lateinit var binding: ActivityMyDetailPostingBinding

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyDetailPostingBinding.inflate(layoutInflater)
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

        binding.bottomAppBar.publicText.setOnClickListener {
            viewModel.changePublic()
        }

        // TODO : EditText

        binding.bottomAppBar.deleteText.setOnClickListener {
            viewModel.deletePosting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    finish()
                }, {
                    Timber.d(it)
                })
                .also { compositeDisposable.add(it) }
        }


    }

    private fun modelView(posting: PostingDto) {

        when(posting.isPublic) {
            true -> {
                binding.bottomAppBar.publicText.background = ContextCompat.getDrawable(this, R.drawable.layout_public_background)
                binding.bottomAppBar.publicText.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
            false -> {
                binding.bottomAppBar.publicText.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
                binding.bottomAppBar.publicText.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }

        binding.contentText.gravity = when(posting.alignment) {
            "LEFT" -> Gravity.LEFT
            "CENTER" -> Gravity.CENTER
            else -> Gravity.CENTER
        }

        binding.titleText.text = posting.title
        binding.contentText.text = posting.content
        binding.writerText.text = posting.writer.nickname
        binding.createdAtText.text = posting.createdAt
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
