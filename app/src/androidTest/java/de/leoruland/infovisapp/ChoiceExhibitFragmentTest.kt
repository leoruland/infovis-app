package de.leoruland.infovisapp

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.leoruland.infovisapp.controller.ChoiceExhibitFragment
import de.leoruland.infovisapp.model.Topic
import de.leoruland.infovisapp.states.TopicsChoiceStateHolder
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChoiceExhibitFragmentTest {

    private lateinit var scenario: FragmentScenario<ChoiceExhibitFragment>
    private fun showFragment() {
        scenario = launchFragmentInContainer(null, R.style.Theme_Infovisapp_NoActionBar)
    }

    @Before
    fun setup() {
        showFragment()
        TopicsChoiceStateHolder.addTopic(Topic("Mithras"))
    }

    @After
    fun teardown() {
        scenario.close()
        TopicsChoiceStateHolder.clearStore()
    }

    @Test
    fun base_layout_shows() {
        showFragment()

        ChoiceExhibitScreen.apply {
            title.isVisible()
            backButton.isVisible()
            backButton.isClickable()
            numberInputButton.isVisible()
            numberInputButton.isClickable()
            exhibitItems.isVisible()
        }
    }

}