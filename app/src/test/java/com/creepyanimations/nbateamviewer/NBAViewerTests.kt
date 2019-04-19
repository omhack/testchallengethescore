package com.creepyanimations.nbateamviewer

import android.content.Context
import com.creepyanimations.nbateamviewer.models.Team
import com.creepyanimations.nbateamviewer.services.TeamsService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class NBAViewerTests {

    val expectedTeams = listOf(Team(), Team())

    @Mock
    lateinit var teamsService: TeamsService

    @Mock
    var mMockContext: Context? = null

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(teamsService.getTeams(any(),any())).
            thenReturn(Single.create{ emitter ->
                emitter.onSuccess(expectedTeams)
            })
    }

    @Test
    fun testGetTeams() {

            mMockContext?.let {
                teamsService.getTeams("1akzl0", it).subscribe { repos ->
                    repos.forEach(::println)
                }
            }

    }


}