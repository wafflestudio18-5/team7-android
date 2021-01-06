package com.wafflestudio.written.ui.main.subscribe.detail_posting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivitySubscribeDetailPostingBinding
import com.wafflestudio.written.models.PostingDto
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_subscribe_detail_posting.*
import kotlinx.android.synthetic.main.subscribe_bottom_app_bar_detail_posting.view.*
import timber.log.Timber

class SubscribeDetailPostingActivity : AppCompatActivity() {

    private val viewModel: SubscribeDetailPostingViewModel by viewModels()
    private lateinit var binding: ActivitySubscribeDetailPostingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscribeDetailPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        viewModel.posting = intent.getParcelableExtra("posting")!!

        bottom_app_bar.writer_text.text = viewModel.posting.writer.nickname
        title_text.text = viewModel.posting.title
        content_text.text = viewModel.posting.content
        writer_text.text = viewModel.posting.title
        created_at_text.text = viewModel.posting.createdAt

        // TODO: Bottom bar OnClickListener

        viewModel.getPostingDetail(viewModel.posting.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModel.posting = it
                title_text.text = it.title
                content_text.text = it.content
                writer_text.text = it.title
                created_at_text.text = it.createdAt
            }, {
                Timber.d(it)
            })

        bottom_app_bar.back_text.setOnClickListener {
            finish()
        }
    }

}
