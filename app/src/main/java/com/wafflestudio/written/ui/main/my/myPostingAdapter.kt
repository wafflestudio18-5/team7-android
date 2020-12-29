package com.wafflestudio.written.ui.main.my

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.models.PostingDto

class myPostingAdapter(context: Context) : RecyclerView.Adapter<myPostingViewHolder>() {
    private val context = context
    var postings: List<PostingDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myPostingViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posting_my, parent, false)
            .let { myPostingViewHolder(it, context) }


    override fun getItemCount(): Int {
        return postings.size
    }

    override fun onBindViewHolder(holder: myPostingViewHolder, position: Int) {
        val posting = postings[position]
        holder.render(posting)
    }
}

class myPostingViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {


    fun render(posting: PostingDto) {

    }
}
