package com.wafflestudio.written.ui.main.my

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.models.PostingDto
import kotlinx.android.synthetic.main.item_posting_my.view.*

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
    private var titleText: TextView = view.title_text
    private var contentText: TextView = view.content_text
    private var createdAtText: TextView = view.created_at_text

    fun render(posting: PostingDto) {
        titleText.text = posting.title
        contentText.text = posting.content
        createdAtText.text = posting.createdAt // TODO : String 수정해서 넣기
    }
}
