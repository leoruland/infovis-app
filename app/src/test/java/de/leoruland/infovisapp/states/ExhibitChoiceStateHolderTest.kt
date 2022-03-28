package de.leoruland.infovisapp.states

import de.leoruland.infovisapp.model.Exhibit
import de.leoruland.infovisapp.model.Topic
import org.junit.Assert.assertEquals
import org.junit.Test
import org.osmdroid.util.GeoPoint

class ExhibitChoiceStateHolderTest {

    private val firstExhibit = Exhibit(
        "URL_ID",
        "1",
        "erstes",
        "museum",
        GeoPoint(0.0, 0.0),
        "Es ist ein Stück.",
        listOf(Topic("Test"), Topic("Fall"), Topic("Eins")),
    )

    private val secondExhibit = Exhibit(
        "URL_ID",
        "2",
        "zweites",
        "ausstellung",
        GeoPoint(90.0, 180.0),
        "Es ist sehr gut.",
        listOf(
            Topic("Test"), Topic("Fall"), Topic("Zwei")
        ),
    )

    @Test
    fun exhibit_is_set_and_get_correctly() {
        ExhibitChoiceStateHolder.setExhibit(firstExhibit)
        val exhibit = ExhibitChoiceStateHolder.getExhibit()
        assertEquals("URL_ID", exhibit!!.id)
        assertEquals("1", exhibit.number)
        assertEquals("erstes", exhibit.name)
        assertEquals("museum", exhibit.repository)
        assertEquals(GeoPoint(0.0, 0.0), exhibit.location)
        assertEquals("Es ist ein Stück.", exhibit.description)
        assertEquals(
            listOf(
                Topic("Test"), Topic("Fall"), Topic("Eins")
            ),
            exhibit.topics
        )
    }

    @Test
    fun exhibit_is_re_set_and_get_correctly() {
        ExhibitChoiceStateHolder.setExhibit(firstExhibit)
        ExhibitChoiceStateHolder.setExhibit(secondExhibit)
        val exhibit = ExhibitChoiceStateHolder.getExhibit()
        assertEquals("URL_ID", exhibit!!.id)
        assertEquals("2", exhibit.number)
        assertEquals("zweites", exhibit.name)
        assertEquals("ausstellung", exhibit.repository)
        assertEquals(GeoPoint(90.0, 180.0), exhibit.location)
        assertEquals("Es ist sehr gut.", exhibit.description)
        assertEquals(
            listOf(
                Topic("Test"), Topic("Fall"), Topic("Zwei")
            ),
            exhibit.topics
        )

    }
}