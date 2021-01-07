package com.wafflestudio.written.ui.main.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.databinding.FragmentMyBinding
import com.wafflestudio.written.models.PostingDto
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_my.*
import timber.log.Timber

class MyFragment : Fragment() {

    private lateinit var binding: FragmentMyBinding
    private val myViewModel: MyViewModel by viewModels()
    private lateinit var myPostingAdapter: MyPostingAdapter
    private lateinit var myLayoutManager: LinearLayoutManager

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPostingAdapter = MyPostingAdapter(this.requireContext())
        myLayoutManager = LinearLayoutManager(this.context)
        my_postings_recyclerview.layoutManager = myLayoutManager
        my_postings_recyclerview.adapter = myPostingAdapter

        myViewModel.observePostings().subscribe {
            myPostingAdapter.postings = it
        }

        // get initial values
        myViewModel.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { user ->
                myViewModel.getMyPostings()
                    .map { user }
            }
            .subscribe({ user ->
                binding.nicknameText.text = user.nickname
                binding.descriptionText.text = user.description
            }, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }

        my_postings_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalPostings = myPostingAdapter.itemCount
                val lastVisiblePostingPosition =
                    myLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePostingPosition >= totalPostings - 1) {
                    myViewModel.getNextPostings()
                }

            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.dispose()
    }


}
