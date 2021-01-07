package com.wafflestudio.written.ui.main.my.detail_posting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivityMyDetailPostingBinding
import com.wafflestudio.written.models.PostingDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDetailPostingActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, MyDetailPostingActivity::class.java)
        }
    }

    private val viewModel: MyDetailPostingViewModel by viewModels()
    private lateinit var binding: ActivityMyDetailPostingBinding

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

        }


        // TODO: Bottom bar OnClickListener



    }

    private fun modelView(posting: PostingDto) {
        binding.titleText.text = posting.title
        binding.contentText.text = posting.content
        binding.writerText.text = posting.writer.nickname
        binding.createdAtText.text = posting.createdAt
    }
}
