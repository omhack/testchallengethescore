package com.creepyanimations.nbateamviewer.modules

import com.creepyanimations.nbateamviewer.TeamsViewModel
import com.creepyanimations.nbateamviewer.services.TeamsService
import com.creepyanimations.nbateamviewer.services.TeamsServiceImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object NBATeamViewerDependencyInjectionModule {

    fun getModule(): Module = module {

        viewModel { TeamsViewModel(get()) }

        single<TeamsService>{ TeamsServiceImpl() }
    }
}