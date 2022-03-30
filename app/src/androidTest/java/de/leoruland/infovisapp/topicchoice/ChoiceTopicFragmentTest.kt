package de.leoruland.infovisapp.topicchoice

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.leoruland.infovisapp.ChoiceTopicScreen
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.states.TopicsChoiceStateHolder
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ChoiceTopicFragmentTest {

    private lateinit var scenario: FragmentScenario<ChoiceTopicFragment>
    private lateinit var navController: TestNavHostController

    private fun showFragment() {
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        scenario = launchFragmentInContainer(null, R.style.Theme_Infovisapp_NoActionBar) {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.ChoiceTopicFragment)

            ChoiceTopicFragment().also { fragment ->
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
        val firstTopicTitle = "Mithras"
        val topicsTotal = 6

        showFragment()
        // Kakao Tests
        ChoiceTopicScreen {
            title.isVisible()
            nextButton {
                isVisible()
                isClickable()
            }
            numberInputButton {
                isVisible()
                isClickable()
            }
            topicItems {
                isVisible()
                hasSize(topicsTotal)
                firstChild<ChoiceTopicScreen.TopicItem> {
                    title.hasText(firstTopicTitle)
                }
            }
        }
        // Espresso Tests
        onView(withId(R.id.header)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_next)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_next)).check(matches(isClickable()))
        onView(withId(R.id.fab_numberinput)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_numberinput)).check(matches(isClickable()))
        onView(withId(R.id.topicRecyclerView)).check(matches(isDisplayed()))
        assertEquals(
            getCountFromRecyclerView(R.id.topicRecyclerView),
            topicsTotal
        )
        onView(withId(R.id.topicRecyclerView)).check(matches(hasDescendant(withText(firstTopicTitle))))
    }

    /**
     * Hilfsfunktion, zur Prüfung der Unterelemente einer RecyclerView
     * Quelle: https://stackoverflow.com/a/47943069/11190851
     * am 30.03.2022
     */
    private fun getCountFromRecyclerView(@IdRes RecyclerViewId: Int): Int {
        val count = intArrayOf(0)
        val matcher: Matcher<View> = object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View): Boolean {
                count[0] = (item as RecyclerView).adapter!!.itemCount
                return true
            }

            override fun describeTo(description: Description) {}
        }
        onView(allOf(withId(RecyclerViewId), isDisplayed())).check(matches(matcher))
        return count[0]
    }

    @Test
    fun select_topics_and_confirm_navigates_to_exhibit_choice() {
        showFragment()

        ChoiceTopicScreen {
            topicItems {
                firstChild<ChoiceTopicScreen.TopicItem> {
                    click()
                }
            }
            nextButton.click()
        }

        assertEquals(
            R.id.ChoiceExhibitFragment,
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