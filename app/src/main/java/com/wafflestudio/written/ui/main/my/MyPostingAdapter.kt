package com.wafflestudio.written.ui.main.my

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.extension.StringExtension
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.ui.main.my.detail_posting.MyDetailPostingActivity
import kotlinx.android.synthetic.main.item_posting_my.view.*

class MyPostingAdapter(private val context: Context) : RecyclerView.Adapter<MyPostingViewHolder>() {
    var postings: List<PostingDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostingViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posting_my, parent, false)
            .let { MyPostingViewHolder(it) }

    override fun getItemCount(): Int {
        return postings.size
    }

    override fun onBindViewHolder(holder: MyPostingViewHolder, position: Int) {
        val posting = postings[position]
        holder.render(posting)

        holder.itemView.setOnClickListener {
            val intent = MyDetailPostingActivity.createIntent(context)
            intent.putExtra("posting", posting)
            context.startActivity(intent)
        }
    }
}

class MyPostingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var titleText: TextView = view.title_text
    private var contentText: TextView = view.content_text
    private var createdAtText: TextView = view.created_at_text
    private var publicText: TextView = view.public_text


    fun render(posting: PostingDto) {
        titleText.text = posting.title
        contentText.text = modifyContent(posting.content)
        createdAtText.text = StringExtension().modifyCreatedAt(posting.createdAt)
        when (posting.isPublic) {
            true -> {
                publicText.visibility = VISIBLE
            }
            false -> {
                publicText.visibility = INVISIBLE
            }
        }
    }

    private fun modifyContent(content: String): String {
        val lines = content.split('\n')
        if(lines.size < 2) {
            return lines[0] + '\n' + "..."
        }
        else {
            return lines[0] + '\n' + lines[1].substring(0, 5) + "..."
        }
    }
}
