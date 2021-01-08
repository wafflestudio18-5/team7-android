package com.wafflestudio.written.ui.main.saved

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.extension.StringExtension
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.ui.main.saved.detail_posting.SavedDetailPostingActivity
import kotlinx.android.synthetic.main.item_posting_saved.view.*

class SavedPostingAdapter(private val context: Context) : RecyclerView.Adapter<SavedPostingViewHolder>() {
    var postings: List<PostingDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPostingViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posting_saved, parent, false)
            .let { SavedPostingViewHolder(it) }


    override fun getItemCount(): Int {
        return postings.size
    }

    override fun onBindViewHolder(holder: SavedPostingViewHolder, position: Int) {
        val posting = postings[position]
        holder.render(posting)

        holder.itemView.setOnClickListener {
            val intent = SavedDetailPostingActivity.createIntent(context)
            intent.putExtra("posting", posting)
            context.startActivity(intent)
        }
    }
}

class SavedPostingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var titleText: TextView = view.title_text
    private var contentText: TextView = view.content_text
    private var createdAtText: TextView = view.created_at_text



    fun render(posting: PostingDto) {
        titleText.text = posting.title
        contentText.text = modifyContent(posting.content)
        createdAtText.text = StringExtension().modifyCreatedAt(posting.createdAt)
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