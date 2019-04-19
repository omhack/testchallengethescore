package com.creepyanimations.nbateamviewer.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.creepyanimations.nbateamviewer.R
import com.creepyanimations.nbateamviewer.activities.TeamDetailActivity
import com.creepyanimations.nbateamviewer.models.Team
import com.creepyanimations.nbateamviewer.shared.BaseActivity.Companion.selectedTeam
import com.creepyanimations.nbateamviewer.shared.listen
import kotlinx.android.synthetic.main.item_team.view.*

class TeamsAdapter(private var teams: List<Team>, private val activity: Activity?) :
    RecyclerView.Adapter<TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        val card = view.findViewById(R.id.cardTeam) as CardView

        return TeamsViewHolder(card).listen { pos, type ->
            val team = teams[pos]
            selectedTeam.postValue(team)
            val i = Intent(parent.context, TeamDetailActivity::class.java)
            parent.context.startActivity(i)
        }
    }

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        if (holder != null) {
            val team = teams[position]
            holder.card.tvTeamName.text = team.full_name
            holder.card.tvWins.text = activity!!.getString(R.string.wins, team.wins.toString())
            holder.card.tvLosses.text =  activity!!.getString(R.string.losses, team.losses.toString())
        }
    }

    fun refreshTeams(teams: List<Team>) {
        this.teams = teams
        notifyDataSetChanged()
    }
}

class TeamsViewHolder(val card: View) : RecyclerView.ViewHolder(card)