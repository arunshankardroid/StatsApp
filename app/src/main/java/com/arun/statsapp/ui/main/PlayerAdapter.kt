package com.arun.statsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arun.statsapp.R
import com.arun.statsapp.events.RxEventBus
import com.arun.statsapp.model.Player
import com.bumptech.glide.Glide

class PlayerAdapter(private val players: MutableList<Player>) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        val context = holder.teamHeadShot.context
        holder.teamHeadShot.loadImage(
            context.getString(R.string.imageUrl,player.id.toString()),
            context.getString(R.string.fallbackImageUrl)
        )
        holder.teamShortName.text = context.getString(R.string.name,player.shortName)
        holder.teamJumperNumber.text = context.getString(R.string.jumper,player.jumperNumber.toString())
        holder.teamPosition.text = context.getString(R.string.position,player.position)
        holder.teamStatValue.text = context.getString(R.string.stats,player.statValue.toString())
        holder.playerLayout.setOnClickListener {
            RxEventBus.publish(PlayerClicked(player))
        }
    }

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerLayout: LinearLayout = view.findViewById(R.id.playerLayout)
        val teamHeadShot: ImageView = view.findViewById(R.id.teamHeadShot)
        val teamShortName: TextView = view.findViewById(R.id.teamShortName)
        val teamJumperNumber: TextView = view.findViewById(R.id.teamJumperNumber)
        val teamPosition: TextView = view.findViewById(R.id.teamPosition)
        val teamStatValue: TextView = view.findViewById(R.id.teamStatValue)
    }

}
fun ImageView.loadImage(imageUrl: String, fallbackImageUrl: String) =
    Glide.with(context)
        .load(imageUrl)
        .error(Glide.with(context).load(fallbackImageUrl))
        .into(this)