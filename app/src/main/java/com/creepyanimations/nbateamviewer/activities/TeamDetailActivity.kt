package com.creepyanimations.nbateamviewer.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creepyanimations.nbateamviewer.R
import com.creepyanimations.nbateamviewer.adapters.PlayerAdapter
import com.creepyanimations.nbateamviewer.models.Player
import com.creepyanimations.nbateamviewer.shared.BaseActivity
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : BaseActivity() {

    private var players: List<Player> = emptyList()
    private lateinit var playersAdapter: PlayerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        configUI()
    }

    fun configUI() {

        playersAdapter = PlayerAdapter(emptyList(), this)
        rvPlayers.setHasFixedSize(true)
        rvPlayers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
        rvPlayers.adapter = playersAdapter

        selectedTeam.observe(this, Observer { team ->
            team.let {
                tvDetailTeamName.text = team.full_name
                tvDetailWins.text =  getString(R.string.wins, team.wins.toString())
                tvDetailLosses.text = getString(R.string.losses, team.losses.toString())

                players = team.players?.sortedBy { player -> player.first_name } ?: emptyList()

                players.let {
                    if(!players.isEmpty())
                    playersAdapter.refreshPlayers(players)
                }

            }
        })
    }
}
