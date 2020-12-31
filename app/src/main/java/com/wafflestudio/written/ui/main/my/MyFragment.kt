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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_my.*

class MyFragment : Fragment() {

    private lateinit var binding: FragmentMyBinding
    private val myViewModel: MyViewModel by viewModels()
    private lateinit var myPostingAdapter: MyPostingAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private var loadingPostings: Boolean = false

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

        myViewModel.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                myViewModel.user = it
                binding.nicknameText.text = it.nickname
                binding.descriptionText.text = it.description

                loadingPostings = true
                myViewModel.getMyPostings(userId = it.id, cursor = null, pageSize = 20)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        myViewModel.cursor = it.cursor
                        myViewModel.hasNextPosting = it.hasNext
                        myPostingAdapter.postings.union(it.postings)
                        myPostingAdapter.notifyDataSetChanged()
                    }, {
                        loadingPostings = false
                        // TODO : ERROR HANDLING
                    })

            }, {
                // TODO : ERROR HANDLING
            })

        my_postings_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalPostings = myPostingAdapter.itemCount
                val lastVisiblePostingPosition = myLayoutManager.findLastCompletelyVisibleItemPosition()

                if(lastVisiblePostingPosition >= totalPostings - 1) {
                    if(!loadingPostings)
                    {
                        loadingPostings = true
                        myViewModel.getMyPostings(userId = myViewModel.user.id, cursor = myViewModel.cursor, pageSize = 20)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                myViewModel.cursor = it.cursor
                                myViewModel.hasNextPosting = it.hasNext
                                myPostingAdapter.postings.union(it.postings)
                                myPostingAdapter.notifyDataSetChanged()
                                loadingPostings = false
                            }, {
                                loadingPostings = false
                                // TODO : ERROR HANDLING
                            })
                    }
                }

            }
        })


    }


}
