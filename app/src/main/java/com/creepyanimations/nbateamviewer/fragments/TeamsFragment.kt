package com.creepyanimations.nbateamviewer.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creepyanimations.nbateamviewer.R
import com.creepyanimations.nbateamviewer.TeamsViewModel
import com.creepyanimations.nbateamviewer.adapters.TeamsAdapter
import com.creepyanimations.nbateamviewer.models.Team
import com.creepyanimations.nbateamviewer.shared.BaseFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_teams.*
import org.koin.androidx.viewmodel.ext.sharedViewModel

class TeamsFragment : BaseFragment(){

    val teamsViewModel: TeamsViewModel by sharedViewModel()
    private lateinit var teamsAdapter: TeamsAdapter
    private var teams: List<Team> = emptyList()
    private var filter: String? = null

    companion object {
        val TAG: String = TeamsFragment.javaClass.simpleName
    }

    override fun layoutId() = R.layout.fragment_teams

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configUI()
    }

    fun configUI() {
        filterOptionTabs.addTab(filterOptionTabs.newTab().setText("ABC"))
        filterOptionTabs.addTab(filterOptionTabs.newTab().setText("Wins"))
        filterOptionTabs.addTab(filterOptionTabs.newTab().setText("Losses"))

        teamsAdapter = TeamsAdapter(emptyList(), activity)
        rvTeams.setHasFixedSize(true)
        rvTeams.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvTeams.adapter = teamsAdapter

        teamsViewModel.currentTeams.observe(this, Observer {
            //(activity as? BaseActivity)?.hideLoading()
            teams =  it.sortedBy { team -> team.full_name }
            teamsAdapter.refreshTeams(teams = teams )
        })

        filterOptionTabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                filter = tab.text.toString()
                if (teams != null && !teams.isEmpty()) {

                    when(filter){
                        "ABC"->{
                            teams =  teams.sortedBy { team -> team.full_name }
                        }
                        "Wins"->{
                            teams = teams.sortedByDescending { team -> team.wins }
                        }
                        "Losses"->{
                            teams = teams.sortedByDescending { team -> team.losses }
                        }
                        else ->{}

                    }

                    teamsAdapter.refreshTeams(teams )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}