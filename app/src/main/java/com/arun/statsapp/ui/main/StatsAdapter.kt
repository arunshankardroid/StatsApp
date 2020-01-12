package com.arun.statsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arun.statsapp.R
import com.arun.statsapp.model.Stat


class StatsAdapter(private var statItems: List<StatItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType {
        STAT_TYPE,
        PLAYER
    }

    override fun getItemViewType(position: Int): Int {
        return statItems[position].viewType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ViewType.PLAYER.ordinal) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_view, parent, false)
            GridViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_stat_title, parent, false)
            StatTypeViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if(viewHolder is GridViewHolder) {
            viewHolder.gridView.apply {
                layoutManager = GridLayoutManager(this.context, 2)
                var dividerItemDecoration = DividerItemDecoration(
                    this.context,
                    RecyclerView.HORIZONTAL
                )
                this.addItemDecoration(dividerItemDecoration)
                dividerItemDecoration = DividerItemDecoration(
                    this.context,
                    RecyclerView.VERTICAL
                )
                this.addItemDecoration(dividerItemDecoration)
                val stat = statItems[position].data as Stat
                val players = merge(stat.teamA.topPlayers,stat.teamB.topPlayers)
                adapter = PlayerAdapter(players)
                setRecycledViewPool(RecyclerView.RecycledViewPool())
            }
        } else if(viewHolder is StatTypeViewHolder){
            viewHolder.statTypeTV.text = statItems[position].data as String
        }

    }

    override fun getItemCount(): Int {
        return statItems.size
    }

    class StatTypeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statTypeTV: TextView = view.findViewById(R.id.stat_type)
    }

    class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gridView: RecyclerView = view.findViewById(R.id.gridView)
    }

}

data class StatItem(val viewType: StatsAdapter.ViewType, val data: Any)

fun View.showView(shouldShow: Boolean) {
    if (shouldShow) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun <T> merge(
    a: Collection<T>,
    b: Collection<T>
): ArrayList<T> {
    val itA = a.iterator()
    val itB = b.iterator()
    val result = ArrayList<T>()
    while (itA.hasNext() || itB.hasNext()) {
        if (itA.hasNext()) result.add(itA.next())
        if (itB.hasNext()) result.add(itB.next())
    }
    return result
}
