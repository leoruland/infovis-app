package de.leoruland.infovisapp.controller

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.leoruland.infovisapp.ChoiceExhibitScreen
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.model.Topic
import de.leoruland.infovisapp.states.TopicsChoiceStateHolder
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChoiceExhibitFragmentTest {

    private lateinit var scenario: FragmentScenario<ChoiceExhibitFragment>
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
        val firstExhibitTitle =
            "#1 Statue des Mithras einer Mithras-Gruppe aus Venedig, Museo Archeologico Nazionale di Venezia"
        val exhibitsTotal = 3
        TopicsChoiceStateHolder.addTopic(Topic("Mithras"))

        showFragment()

        ChoiceExhibitScreen {
            title.isVisible()
            backButton.isVisible()
            backButton.isClickable()
            numberInputButton.isVisible()
            numberInputButton.isClickable()
            exhibitItems {
                isVisible()
                hasSize(exhibitsTotal)
                firstChild<ChoiceExhibitScreen.ExhibitItem> {
                    title.hasText(firstExhibitTitle)
                }
            }
        }
    }

    @Test
    fun empty_topics_shows_empty_list() {
        TopicsChoiceStateHolder.clearStore()

        showFragment()

        ChoiceExhibitScreen {
            exhibitItems.hasSize(0)
        }
    }
}