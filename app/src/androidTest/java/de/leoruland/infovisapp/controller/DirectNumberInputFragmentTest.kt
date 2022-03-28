package de.leoruland.infovisapp.controller

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.states.TopicsChoiceStateHolder
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DirectNumberInputFragmentTest {

    private lateinit var scenario: FragmentScenario<DirectNumberInputFragment>
    private fun showFragment() {
        scenario = launchFragmentInContainer(null, R.style.Theme_Infovisapp_NoActionBar)
    }

    @After
    fun teardown() {
        scenario.close()
        TopicsChoiceStateHolder.clearStore()
    }

    @Test
    fun layout_shows_completely() {
        showFragment()
    }
}