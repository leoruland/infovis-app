package de.leoruland.infovisapp.exhibitchoice

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.leoruland.infovisapp.ChoiceExhibitScreen
import de.leoruland.infovisapp.ChoiceTopicScreen
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.repository.Topic
import de.leoruland.infovisapp.states.TopicsChoiceStateHolder
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChoiceExhibitFragmentTest {

    private lateinit var scenario: FragmentScenario<ChoiceExhibitFragment>
    private lateinit var navController: TestNavHostController

    private fun showFragment() {
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        scenario = launchFragmentInContainer(null, R.style.Theme_Infovisapp_NoActionBar) {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.ChoiceExhibitFragment)

            ChoiceExhibitFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }
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
            backButton {
                isVisible()
                isClickable()
            }
            numberInputButton {
                isVisible()
                isClickable()
            }
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

    @Test
    fun click_on_exhibit_navigates_to_exhibit_detail() {
        TopicsChoiceStateHolder.addTopic(Topic("Mithras"))

        showFragment()

        ChoiceExhibitScreen {
            exhibitItems.firstChild<ChoiceExhibitScreen.ExhibitItem> {
                click()
            }
        }

        assertEquals(
            R.id.DetailExhibitFragment,
            navController.currentDestination?.id
        )
    }

    @Test
    fun back_button_navigates_to_topics_choice() {
        showFragment()

        ChoiceExhibitScreen.backButton.click()

        assertEquals(
            R.id.ChoiceTopicFragment,
            navController.currentDestination?.id
        )
    }

    @Test
    fun number_input_button_navigates_to_number_input_screen() {
        showFragment()

        ChoiceTopicScreen.numberInputButton.click()

        assertEquals(
            R.id.DirectNumberInputFragment,
            navController.currentDestination?.id
        )
    }
}