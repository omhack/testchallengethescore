package com.creepyanimations.nbateamviewer.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.creepyanimations.nbateamviewer.R
import com.creepyanimations.nbateamviewer.TeamsViewModel
import com.creepyanimations.nbateamviewer.fragments.TeamsFragment
import com.creepyanimations.nbateamviewer.shared.BaseActivity
import org.koin.androidx.viewmodel.ext.viewModel

class MainActivity : BaseActivity() {

    private val teamsViewModel: TeamsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openMainFragment()

    }

    private fun openMainFragment() {
        replaceFragment(R.id.fragmentContainer, TeamsFragment(), TeamsFragment.TAG)
        teamsViewModel.getTeams("1akzl0", context = applicationContext)
    }

    private fun replaceFragment(containerViewId: Int, fragment: Fragment, TAG: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerViewId, fragment, TAG)
        fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commitAllowingStateLoss()
    }
}
