package com.wafflestudio.written.ui.main.subscribe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.databinding.FragmentSubscribeBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_subscribe.*
import timber.log.Timber

class SubscribeFragment : Fragment() {

    private lateinit var binding: FragmentSubscribeBinding
    private val subscribeViewModel: SubscribeViewModel by viewModels()
    private lateinit var subscribePostingAdapter: SubscribePostingAdapter
    private lateinit var subscribeLayoutManager: LinearLayoutManager

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubscribeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribePostingAdapter = SubscribePostingAdapter(this.requireContext())
        subscribeLayoutManager = LinearLayoutManager(this.context)
        subscribe_recyclerview.layoutManager = subscribeLayoutManager
        subscribe_recyclerview.adapter = subscribePostingAdapter

        subscribeViewModel.observePostings().subscribe {
            subscribePostingAdapter.postings = it
        }

        subscribeViewModel.getSubscribedPostings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }

        subscribe_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalPostings = subscribePostingAdapter.itemCount
                val lastVisiblePostingPosition =
                    subscribeLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePostingPosition >= totalPostings - 1) {
                    subscribeViewModel.getNextPostings()
                }
            }
        }
        )

    }

}
