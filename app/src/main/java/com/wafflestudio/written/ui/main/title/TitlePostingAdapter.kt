package com.wafflestudio.written.ui.main.title

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wafflestudio.written.R
import com.wafflestudio.written.models.TitleDto
import com.wafflestudio.written.ui.main.title.detail_title.TitleDetailPostingsActivity
import kotlinx.android.synthetic.main.item_title.view.*

class TitlePostingAdapter(private val context: Context) : RecyclerView.Adapter<TitlePostingViewHolder>() {

    var titles: List<TitleDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitlePostingViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_title, parent, false)
            .let { TitlePostingViewHolder(it) }


    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: TitlePostingViewHolder, position: Int) {
        val title = titles[position]
        holder.render(title)

        holder.itemView.setOnClickListener {
            val intent = TitleDetailPostingsActivity.createIntent(context)
            intent.putExtra("titleId", title.id.toInt())
            context.startActivity(intent)
        }
    }
}


class TitlePostingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var titleText: TextView = view.title_text
    private var titleNumText: TextView = view.title_number_text
    private var writtenOpenText: TextView = view.written_number_with_open_number_text

    fun render(title: TitleDto) {
        titleText.text = title.name
        titleNumText.text = "# " + title.id
        writtenOpenText.text = modifyWrittenOpen(title.countPublicPostings, title.countAllPostings)
    }

    private fun modifyWrittenOpen(countPublic: Int, countAll: Int): String {
        return countAll.toString() + "씀 / " + countPublic.toString() + "공개"
    }
}
