package com.wafflestudio.written.ui.main.my.detail_posting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivityMyDetailPostingBinding
import com.wafflestudio.written.models.PostingDto
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_detail_posting.*
import kotlinx.android.synthetic.main.my_bottom_app_bar_detail_posting.view.*
import timber.log.Timber

@AndroidEntryPoint
class MyDetailPostingActivity : AppCompatActivity() {

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


        // TODO: Bottom bar OnClickListener



    }

    private fun modelView(posting: PostingDto) {
        binding.titleText.text = posting.title
        binding.contentText.text = posting.content
        binding.writerText.text = posting.writer.nickname
        binding.createdAtText.text = posting.createdAt
    }
}
