package com.wafflestudio.written.ui.main.subscribe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.ui.main.subscribe.detail_posting.SubscribeDetailPostingActivity
import kotlinx.android.synthetic.main.item_posting.view.*

class SubscribePostingAdapter(private val context: Context) : RecyclerView.Adapter<SubscribePostingViewHolder>() {

    var postings: List<PostingDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribePostingViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posting, parent, false)
            .let { SubscribePostingViewHolder(it) }


    override fun getItemCount(): Int {
        return postings.size
    }

    override fun onBindViewHolder(holder: SubscribePostingViewHolder, position: Int) {
        val posting = postings[position]
        holder.render(posting)

        holder.itemView.setOnClickListener {
            val intent = SubscribeDetailPostingActivity.createIntent(context)
            intent.putExtra("posting", posting)
            context.startActivity(intent)
        }
    }

}



class SubscribePostingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var titleText: TextView = view.title_text
    private var contentText: TextView = view.content_text
    private var createdAtText: TextView = view.created_at_text
    private var writerText: TextView = view.writer_text

    fun render(posting: PostingDto) {
        titleText.text = posting.title
        contentText.text = posting.content
        createdAtText.text = posting.createdAt // TODO
        writerText.text = posting.writer.nickname
    }


}
