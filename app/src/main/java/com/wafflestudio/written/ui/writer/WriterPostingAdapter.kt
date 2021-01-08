package com.wafflestudio.written.ui.writer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.extension.StringExtension
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.ui.writer.detail_posting.WriterDetailPostingActivity
import kotlinx.android.synthetic.main.item_posting.view.*

class WriterPostingAdapter(private val context: Context) : RecyclerView.Adapter<WriterPostingViewHolder>() {

    var postings: List<PostingDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriterPostingViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posting, parent, false)
            .let { WriterPostingViewHolder(it, context) }


    override fun getItemCount(): Int {
        return postings.size
    }

    override fun onBindViewHolder(holder: WriterPostingViewHolder, position: Int) {
        val posting = postings[position]
        holder.render(posting)

        holder.itemView.setOnClickListener {
            val intent = WriterDetailPostingActivity.createIntent(context)
            intent.putExtra("posting", posting)
            context.startActivity(intent)
        }
    }
}

class WriterPostingViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {
    private var titleText: TextView = view.title_text
    private var contentText: TextView = view.content_text
    private var createdAtText: TextView = view.created_at_text
    private var writerText: TextView = view.writer_text

    fun render(posting: PostingDto) {
        titleText.text = posting.title
        contentText.text = posting.content
        createdAtText.text = StringExtension().modifyCreatedAt(posting.createdAt)
        writerText.text = posting.writer.nickname
    }


}
