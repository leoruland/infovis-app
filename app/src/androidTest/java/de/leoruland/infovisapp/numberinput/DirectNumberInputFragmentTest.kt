package de.leoruland.infovisapp.numberinput

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.leoruland.infovisapp.DirectNumberInputScreen
import de.leoruland.infovisapp.R
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DirectNumberInputFragmentTest {

    private lateinit var scenario: FragmentScenario<DirectNumberInputFragment>
    private lateinit var navController: TestNavHostController

    private fun showFragment() {
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        scenario = launchFragmentInContainer(null, R.style.Theme_Infovisapp_NoActionBar) {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.DirectNumberInputFragment)

            DirectNumberInputFragment().also { fragment ->
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

        DirectNumberInputScreen {
            title.isVisible()
            errorText.isInvisible()
            inputField.isVisible()
            searchButton {
                isVisible()
                isClickable()
            }
            closeButton {
                isVisible()
                isClickable()
            }
        }
    }

    @Test
    fun existing_number_navigates_to_exhibit_detail() {
        showFragment()

        DirectNumberInputScreen {
            inputField {
                typeText("1")
            }
            ViewActions.closeSoftKeyboard()
            searchButton.click()
        }

        assertEquals(
            R.id.DetailExhibitFragment,
            navController.currentDestination?.id

        )
    }

    @Test
    fun non_existing_number_shows_error_message() {
        showFragment()

        DirectNumberInputScreen {
            inputField {
                typeText("111")
            }
            ViewActions.closeSoftKeyboard()
            searchButton.click()
            errorText.isVisible()
        }

        assertEquals(
            R.id.DirectNumberInputFragment,
            navController.currentDestination?.id
        )
    }
}