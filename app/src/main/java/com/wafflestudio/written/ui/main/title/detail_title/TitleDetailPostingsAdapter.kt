package com.wafflestudio.written.ui.main.title.detail_title

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TextView.TEXT_ALIGNMENT_CENTER
import android.widget.TextView.TEXT_ALIGNMENT_TEXT_START
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.extension.StringExtension
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.ui.main.subscribe.SubscribePostingViewHolder
import com.wafflestudio.written.ui.main.subscribe.detail_posting.SubscribeDetailPostingActivity
import kotlinx.android.synthetic.main.item_posting.view.*

class TitleDetailPostingsAdapter(private val context: Context) : RecyclerView.Adapter<TitleDetailPostingsViewHolder>() {

    var postings: List<PostingDto> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TitleDetailPostingsViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posting, parent, false)
            .let { TitleDetailPostingsViewHolder(it) }


    override fun getItemCount(): Int {
        return postings.size
    }

    override fun onBindViewHolder(holder: TitleDetailPostingsViewHolder, position: Int) {
        val posting = postings[position]
        holder.render(posting)

        holder.itemView.setOnClickListener {
            val intent = SubscribeDetailPostingActivity.createIntent(context)
            intent.putExtra("posting", posting)
            context.startActivity(intent)
        }
    }
}




class TitleDetailPostingsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private var titleText: TextView = view.title_text
    private var contentText: TextView = view.content_text
    private var createdAtText: TextView = view.created_at_text
    private var writerText: TextView = view.writer_text

    fun render(posting: PostingDto) {
        titleText.text = posting.title
        contentText.text = posting.content
        contentText.gravity = when(posting.alignment) {
            "LEFT" -> Gravity.LEFT
            "CENTER" -> Gravity.CENTER
            else -> Gravity.CENTER
        }
        createdAtText.text = StringExtension().modifyCreatedAt(posting.createdAt)
        writerText.text = posting.writer.nickname
    }
}
