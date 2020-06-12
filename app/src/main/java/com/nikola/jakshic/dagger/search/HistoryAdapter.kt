package com.nikola.jakshic.dagger.search

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikola.jakshic.dagger.R
import com.nikola.jakshic.dagger.common.inflate
import com.nikola.jakshic.dagger.common.sqldelight.Search_history
import kotlinx.android.synthetic.main.item_search_history.view.*

class HistoryAdapter(val listener: (String) -> Unit) : RecyclerView.Adapter<HistoryAdapter.HistoryVH>() {

    private var list: List<Search_history>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
        return HistoryVH(parent.inflate(R.layout.item_search_history))
    }

    override fun onBindViewHolder(holder: HistoryVH, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount() = list?.size ?: 0

    fun addData(newList: List<Search_history>?) {
        list = newList
        notifyDataSetChanged()
    }

    inner class HistoryVH(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener { listener(list!![adapterPosition].query) }
        }

        fun bind(item: Search_history) {
            with(itemView) {
                tvQuery.text = item.query
            }
        }
    }
}