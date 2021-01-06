package com.wafflestudio.written.ui.main.saved.detail_posting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivitySavedDetailPostingBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_detail_posting.bottom_app_bar
import kotlinx.android.synthetic.main.activity_my_detail_posting.content_text
import kotlinx.android.synthetic.main.activity_my_detail_posting.created_at_text
import kotlinx.android.synthetic.main.activity_my_detail_posting.title_text
import kotlinx.android.synthetic.main.activity_my_detail_posting.writer_text
import kotlinx.android.synthetic.main.saved_bottom_app_bar_detail_posting.view.*
import timber.log.Timber

class SavedDetailPostingActivity : AppCompatActivity() {

    private val viewModel: SavedDetailPostingViewModel by viewModels()
    private lateinit var binding: ActivitySavedDetailPostingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySavedDetailPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        viewModel.posting = intent.getParcelableExtra("posting")!!

        bottom_app_bar.writer_text.text = viewModel.posting.writer.nickname
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
