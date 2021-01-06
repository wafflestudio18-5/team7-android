package com.wafflestudio.written.ui.main.my

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.ui.main.my.detail_posting.MyDetailPostingActivity
import kotlinx.android.synthetic.main.item_posting_my.view.*

class MyPostingAdapter(private val context: Context) : RecyclerView.Adapter<myPostingViewHolder>() {
    var postings: List<PostingDto> = emptyList()
    var hasNext: Boolean = false

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
    private var postingData: PostingDto? = null

    init {
        val itemView = super.itemView
        itemView.setOnClickListener {
            val pos = adapterPosition
            val intent = Intent(context, MyDetailPostingActivity::class.java)
            intent.putExtra("posting", postingData)
        }
    }

    fun render(posting: PostingDto) {
        postingData = posting
        titleText.text = posting.title
        contentText.text = modifyContent(posting.content)
        createdAtText.text = posting.createdAt  // TODO : String 수정해서 넣기
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
