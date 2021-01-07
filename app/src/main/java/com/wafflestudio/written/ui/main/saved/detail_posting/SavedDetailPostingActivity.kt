package com.wafflestudio.written.ui.main.saved.detail_posting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivitySavedDetailPostingBinding
import com.wafflestudio.written.models.PostingDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedDetailPostingActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, SavedDetailPostingActivity::class.java)
        }
    }

    private val viewModel: SavedDetailPostingViewModel by viewModels()
    private lateinit var binding: ActivitySavedDetailPostingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySavedDetailPostingBinding.inflate(layoutInflater)
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

        // TODO : bottom app bar
    }

    private fun modelView(posting: PostingDto) {
        binding.bottomAppBar.writerText.text = posting.writer.nickname
        binding.titleText.text = posting.title
        binding.contentText.text = posting.content
        binding.writerText.text = posting.writer.nickname
        binding.createdAtText.text = posting.createdAt
    }
}
