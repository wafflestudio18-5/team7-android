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
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_my.*
import timber.log.Timber

@AndroidEntryPoint
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
        binding.myPostingsRecyclerview.layoutManager = myLayoutManager
        binding.myPostingsRecyclerview.adapter = myPostingAdapter

        myViewModel.observePostings()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            myPostingAdapter.postings = it
            myPostingAdapter.notifyDataSetChanged()
        }

        // get initial values
        myViewModel.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { user ->
                myViewModel.getMyPostings()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { user }
            }
            .subscribe({ user ->
                binding.nicknameText.text = user.nickname
                binding.descriptionText.text = user.description
            }, {
                Timber.d(it)
            })
            .also { compositeDisposable.add(it) }

        binding.myPostingsRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
