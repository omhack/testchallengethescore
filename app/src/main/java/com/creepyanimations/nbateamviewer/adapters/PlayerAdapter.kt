package com.creepyanimations.nbateamviewer.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.creepyanimations.nbateamviewer.R
import com.creepyanimations.nbateamviewer.models.Player
import com.creepyanimations.nbateamviewer.shared.listen
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerAdapter(private var players: List<Player>, private val activity: Activity?) :
    RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        val card = view.findViewById(R.id.cardPlayer) as CardView

        return PlayerViewHolder(card).listen { pos, type ->
            val player = players[pos] //TODO maybe implement player's details

        }
    }

    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        if (holder != null) {
            val player = players[position]
            holder.card.tvPlayerName.text = activity!!.getString(R.string.player_name, player.first_name, player.last_name)
            holder.card.tvPosition.text = activity!!.getString(R.string.position, player.position)
            holder.card.tvNumber.text = activity!!.getString(R.string.number, player.number.toString())
        }
    }

    fun refreshPlayers(players: List<Player>) {
        this.players = players
        notifyDataSetChanged()
    }
}

class PlayerViewHolder(val card: View) : RecyclerView.ViewHolder(card)