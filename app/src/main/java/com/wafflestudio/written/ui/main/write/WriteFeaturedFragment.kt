package com.wafflestudio.written.ui.main.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.databinding.FragmentWriteFeaturedBinding
import com.wafflestudio.written.databinding.FragmentWriteHomeBinding
import com.wafflestudio.written.ui.main.title.detail_title.TitleDetailPostingsAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

@AndroidEntryPoint
class WriteFeaturedFragment : Fragment() {

    companion object {
        fun newInstance(): WriteFeaturedFragment = WriteFeaturedFragment()
    }

    private lateinit var binding: FragmentWriteFeaturedBinding
    private val viewModel: WriteFeaturedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteFeaturedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = WriteFeaturedAdapter(requireActivity())
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.titlePostingsRecyclerview.adapter = adapter
        binding.titlePostingsRecyclerview.layoutManager = layoutManager

        viewModel.observePostings()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.postings = it
                adapter.notifyDataSetChanged()
            }

        viewModel.getPostings()

        binding.titlePostingsRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalPostings = adapter.itemCount
                val lastVisiblePostingPosition =
                    layoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePostingPosition >= totalPostings - 1) {
                    viewModel.getNextPostings()
                }
            }
        })

    }




}
