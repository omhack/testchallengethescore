package com.creepyanimations.nbateamviewer

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creepyanimations.nbateamviewer.models.Team
import com.creepyanimations.nbateamviewer.services.TeamsService

open class TeamsViewModel(val teamsService: TeamsService) : ViewModel() {

    val currentTeams = MutableLiveData<List<Team>>()

    fun getTeams(id : String, context: Context){
        teamsService.getTeams(id, context).subscribe({ teams ->
            currentTeams.postValue(teams)
        },{
            Log.d(javaClass.simpleName,it.localizedMessage)
        })
    }

}