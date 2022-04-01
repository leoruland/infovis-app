package de.leoruland.infovisapp.exhibitdetail

import android.app.Instrumentation
import android.content.Intent
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.leoruland.infovisapp.DetailExhibitScreen
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.data.Exhibit
import de.leoruland.infovisapp.data.Topic
import de.leoruland.infovisapp.states.ExhibitChoiceStateHolder
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.osmdroid.util.GeoPoint

@RunWith(AndroidJUnit4::class)
class DetailExhibitFragmentTest {

    private lateinit var scenario: FragmentScenario<DetailExhibitFragment>
    private lateinit var navController: TestNavHostController
    private val testExhibit = Exhibit(
        "www.test.url",
        "0",
        "testExhibit",
        "demo",
        GeoPoint(0.0, 0.0),
        "",
        listOf(Topic("erstes"), Topic("zweites"), Topic("drittes")),

        )

    private fun showFragment() {
        // Beispielexponat setzen
        ExhibitChoiceStateHolder.setExhibit(testExhibit)
        // Navigationscontroller für Testumgebung setzen
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        // Fragment initialisieren
        scenario = launchFragmentInContainer(null, R.style.Theme_Infovisapp_NoActionBar) {
            // Setup der Navigation
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.DetailExhibitFragment)
            DetailExhibitFragment().also { fragment ->
                // Navigation initialisieren
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

        DetailExhibitScreen {
            title.isVisible()
            repositoryName.isVisible()
            map.isVisible()
            description.isVisible()
            backButton {
                isVisible()
                isClickable()
            }
            numberInputButton {
                isVisible()
                isClickable()
            }
            openBrowser {
                isVisible()
                isClickable()
            }
            topicBubbles {
                isVisible()
                hasSize(3)
                firstChild<DetailExhibitScreen.TopicBubbleItem> {
                    title.hasText("erstes")
                }
            }
        }
    }

    @Test
    fun back_button_navigates_to_topics_choice() {
        showFragment()

        DetailExhibitScreen.backButton.click()

        Assert.assertEquals(
            R.id.ChoiceTopicFragment,
            navController.currentDestination?.id
        )
    }

    @Test
    fun number_input_button_navigates_to_number_input_screen() {
        showFragment()

        DetailExhibitScreen.numberInputButton.click()

        Assert.assertEquals(
            R.id.DirectNumberInputFragment,
            navController.currentDestination?.id
        )
    }

    @Test
    fun open_button_starts_intent() {
        val viewUrlAction = "android.intent.action.VIEW"
        Intents.init()
        // Intent abfangen, welches zum Öffnen einer URL im Browser verwendet wird
        intending(hasAction(viewUrlAction)).respondWith(
            Instrumentation.ActivityResult(
                0,
                Intent()
            )
        )

        showFragment()

        DetailExhibitScreen.openBrowser.click()
        // Intent prüfen, welches zum Öffnen einer URL im Browser verwendet wird
        intended(hasAction(viewUrlAction))
        Intents.release()
    }
}