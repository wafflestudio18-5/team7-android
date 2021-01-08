package com.wafflestudio.written.ui.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.databinding.FragmentSavedBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_saved.*
import timber.log.Timber

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private val savedViewModel: SavedViewModel by viewModels()
    private lateinit var savedPostingAdapter: SavedPostingAdapter
    private lateinit var savedLayoutManager: LinearLayoutManager

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedPostingAdapter = SavedPostingAdapter(this.requireContext())
        savedLayoutManager = LinearLayoutManager(this.context)
        binding.savedPostingsRecyclerview.layoutManager = savedLayoutManager
        binding.savedPostingsRecyclerview.adapter = savedPostingAdapter

        savedViewModel.observePostings()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            savedPostingAdapter.postings = it
            savedPostingAdapter.notifyDataSetChanged()
        }

        savedViewModel.getSavedPostings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }

        binding.savedPostingsRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalPostings = savedPostingAdapter.itemCount
                val lastVisiblePostingPosition =
                    savedLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePostingPosition >= totalPostings - 1) {
                    savedViewModel.getNextPostings()
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}