package com.wafflestudio.written.ui.main.title.detail_title

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.databinding.ActivityTitleDetailPostingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TitleDetailPostingsActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, TitleDetailPostingsActivity::class.java)
        }
    }

    private val viewModel: TitleDetailPostingsViewModel by viewModels()

    private lateinit var binding: ActivityTitleDetailPostingsBinding
    private lateinit var titleDetailPostingsAdapter: TitleDetailPostingsAdapter
    private lateinit var titleDetailLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTitleDetailPostingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.observePostings().subscribe {
            titleDetailPostingsAdapter.postings = it
            titleDetailPostingsAdapter.notifyDataSetChanged()
        }

        val intent = intent
        viewModel.titleId = intent.getIntExtra("titleId", -1)

        viewModel.getPostings()

        binding.titlePostingsRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalPostings = titleDetailPostingsAdapter.itemCount
                val lastVisiblePostingPosition =
                    titleDetailLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePostingPosition >= totalPostings - 1) {
                    viewModel.getNextPostings()
                }
            }
        })

    }


}
