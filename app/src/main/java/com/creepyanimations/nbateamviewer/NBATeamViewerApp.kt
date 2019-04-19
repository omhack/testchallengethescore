package com.creepyanimations.nbateamviewer

import android.app.Application
import com.creepyanimations.nbateamviewer.modules.NBATeamViewerDependencyInjectionModule
import org.koin.core.context.startKoin

class NBATeamViewerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(NBATeamViewerDependencyInjectionModule.getModule())
        }
    }
}