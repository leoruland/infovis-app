package de.leoruland.infovisapp.exhibitdetail

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.leoruland.infovisapp.ChoiceExhibitScreen
import de.leoruland.infovisapp.ChoiceTopicScreen
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.exhibitchoice.ChoiceExhibitFragment
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailExhibitFragmentTest {

    private lateinit var scenario: FragmentScenario<DetailExhibitFragment>
    private lateinit var navController: TestNavHostController

    private fun showFragment() {
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        scenario = launchFragmentInContainer(null, R.style.Theme_Infovisapp_NoActionBar) {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.DetailExhibitFragment)

            DetailExhibitFragment().also { fragment ->
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
    }

    @Test
    fun layout_shows_completely() {
        showFragment()
    }

    @Test
    fun back_button_navigates_to_topics_choice() {
        showFragment()

        ChoiceExhibitScreen.backButton.click()

        Assert.assertEquals(
            R.id.ChoiceTopicFragment,
            navController.currentDestination?.id
        )
    }

    @Test
    fun number_input_button_navigates_to_number_input_screen() {
        showFragment()

        ChoiceTopicScreen.numberInputButton.click()

        Assert.assertEquals(
            R.id.DirectNumberInputFragment,
            navController.currentDestination?.id
        )
    }
}