package com.wafflestudio.written.ui.main.my.detail_posting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivityMyDetailPostingBinding
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

        val intent = intent
        viewModel.posting = intent.getParcelableExtra("posting")!!

        title_text.text = viewModel.posting.title
        content_text.text = viewModel.posting.content
        writer_text.text = viewModel.posting.writer.nickname
        created_at_text.text = viewModel.posting.createdAt

        // TODO: Bottom bar OnClickListener

        viewModel.getPostingDetail(viewModel.posting.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModel.posting = it
                title_text.text = it.title
                content_text.text = it.content
                writer_text.text = it.writer.nickname
                created_at_text.text = it.createdAt
            }, {
                Timber.d(it)
            })

    }
}
