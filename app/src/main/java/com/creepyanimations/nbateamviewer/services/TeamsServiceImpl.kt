package com.creepyanimations.nbateamviewer.services

import android.content.Context
import com.creepyanimations.nbateamviewer.models.Team
import io.reactivex.Single

class TeamsServiceImpl : TeamsService{

    override fun getTeams(id: String, context: Context): Single<List<Team>> {
        return Single.create{emitter ->
            RetrofitService(context).fetchTeams(id).subscribe({item ->
                item.let {
                    emitter.onSuccess(item!!)
                }
            },{
                emitter.onError(it)
            })
        }
    }

}