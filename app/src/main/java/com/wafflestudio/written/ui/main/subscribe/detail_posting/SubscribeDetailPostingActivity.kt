package com.wafflestudio.written.ui.main.subscribe.detail_posting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.SubscribeActivityDetailPostingBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.subscribe_activity_detail_posting.*
import kotlinx.android.synthetic.main.subscribe_bottom_app_bar_detail_posting.view.*

class SubscribeDetailPostingActivity : AppCompatActivity() {

    private val viewModel: SubscribeDetailPostingViewModel by viewModels()
    private lateinit var binding: SubscribeActivityDetailPostingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SubscribeActivityDetailPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val postingId = intent.getLongExtra("postingId", -1)

        viewModel.getPostingDetail(postingId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                bottom_app_bar.writer_text.text = it.writer.nickname
                bottom_app_bar.writer_posting_text.setOnClickListener {

                }

                bottom_app_bar.subscribe_text.setOnClickListener {

                }

            }, {

            })

        bottom_app_bar.back_text.setOnClickListener {
            finish()
        }
    }

}