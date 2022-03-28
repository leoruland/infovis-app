package de.leoruland.infovisapp.controller

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.leoruland.infovisapp.ChoiceTopicScreen
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.states.TopicsChoiceStateHolder
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChoiceTopicFragmentTest {

    private lateinit var scenario: FragmentScenario<ChoiceTopicFragment>
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
        val firstTopicTitle = "Mithras"
        val topicsTotal = 6

        showFragment()

        ChoiceTopicScreen {
            title.isVisible()
            nextButton {
                isVisible()
                isClickable()
            }
            numberInputButton.isVisible()
            numberInputButton.isClickable()
            topicItems {
                isVisible()
                hasSize(topicsTotal)
                firstChild<ChoiceTopicScreen.TopicItem> {
                    title.hasText(firstTopicTitle)
                }
            }
        }
    }
}