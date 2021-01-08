package com.wafflestudio.written.ui.main.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.databinding.FragmentTitleBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable

@AndroidEntryPoint
class TitleFragment : Fragment() {

    private lateinit var binding: FragmentTitleBinding
    private val viewModel: TitleViewModel by viewModels()
    private lateinit var titlePostingAdapter: TitlePostingAdapter
    private lateinit var titleLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titlePostingAdapter = TitlePostingAdapter(this.requireContext())
        titleLayoutManager = LinearLayoutManager(this.context)
        binding.titleRecyclerview.layoutManager = titleLayoutManager
        binding.titleRecyclerview.adapter = titlePostingAdapter

        viewModel.observeTitles().subscribe {
            titlePostingAdapter.titles = it
        }

        viewModel.getTitles()

        binding.titleRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalTitles = titlePostingAdapter.itemCount
                val lastVisibleTitlePosition =
                    titleLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisibleTitlePosition >= totalTitles - 1) {
                    viewModel.getNextTitles()
                }
            }
        })
    }


}
