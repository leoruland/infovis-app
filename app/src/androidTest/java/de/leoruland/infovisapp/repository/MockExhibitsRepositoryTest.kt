package de.leoruland.infovisapp.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import de.leoruland.infovisapp.data.Exhibit
import de.leoruland.infovisapp.data.Topic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.osmdroid.util.GeoPoint

class MockExhibitsRepositoryTest {

    private lateinit var testContext: Context
    private val testExhibit = Exhibit(
        "https://arachne.dainst.org/entity/1081758",
        "1",
        "Statue des Mithras einer Mithras-Gruppe",
        "Venedig, Museo Archeologico Nazionale di Venezia",
        GeoPoint(41.89474, 12.4839),
        "Rundplastik von 110cm Höhe. Abgebildet ist die Szene eine einer Tauroctonie, bei welcher ein Stier von Mithras mit der linken Hand bei den Nüstern gehalten und der Rechten erdolcht wird.",
        listOf(
            Topic("Mithras"),
            Topic("Tauroctonie"),
            Topic("Statue")
        )
    )
    private val testTopics = listOf(
        Topic("Mithras"),
        Topic("Tauroctonie"),
        Topic("Statue"),
        Topic("Relief"),
        Topic("Marmor"),
        Topic("Altar"),
    )

    @Before
    fun setup() {
        testContext = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun load_and_get_exhibits_first_exhibit_matches() {
        MockExhibitsRepository.loadExhibits(testContext, "testExhibits.json")
        val firstExhibit = MockExhibitsRepository.getExhibits(listOf(Topic("Mithras"))).first()

        assertTrue(testExhibit.id == firstExhibit.id)
        assertTrue(testExhibit.number == firstExhibit.number)
        assertTrue(testExhibit.name == firstExhibit.name)
        assertTrue(testExhibit.repository == firstExhibit.repository)
        assertTrue(testExhibit.location.latitude == firstExhibit.location.latitude)
        assertTrue(testExhibit.location.longitude == firstExhibit.location.longitude)
        assertTrue(testExhibit.description == firstExhibit.description)
        testExhibit.topics.map {
            assertTrue(firstExhibit.topics.contains(it))
        }

    }

    @Test
    fun load_and_get_topics_allTopicsMatch() {
        MockExhibitsRepository.loadTopics(testContext, "testExhibits.json")
        val allTopics = MockExhibitsRepository.getTopics()
        testTopics.map {
            assertTrue(allTopics.contains(it))
        }
        assertFalse(allTopics.contains(Topic("nichtvorhanden")))
    }

    @Test
    fun getExhibits_list_not_inclusive() {
        MockExhibitsRepository.loadExhibits(testContext, "testExhibits.json")
        val allExhibits = MockExhibitsRepository.getExhibits(listOf(
            Topic("Mithras"),
            Topic("Statue")
        ), false)
        assertEquals(1,allExhibits.size)

        val firstExhibit = allExhibits.first()

        assertTrue(testExhibit.id == firstExhibit.id)
        assertTrue(testExhibit.number == firstExhibit.number)
        assertTrue(testExhibit.name == firstExhibit.name)
        assertTrue(testExhibit.repository == firstExhibit.repository)
        assertTrue(testExhibit.location.latitude == firstExhibit.location.latitude)
        assertTrue(testExhibit.location.longitude == firstExhibit.location.longitude)
        assertTrue(testExhibit.description == firstExhibit.description)
        testExhibit.topics.map {
            assertTrue(firstExhibit.topics.contains(it))
        }
    }

    @Test
    fun getExhibits_empty_topics_returns_empty_list() {
        MockExhibitsRepository.loadExhibits(testContext, "testExhibits.json")
        val noExhibits = MockExhibitsRepository.getExhibits(null)
        assertEquals(emptyList<Exhibit>(), noExhibits)
    }

    @Test
    fun getExhibit_existing_returns_exhibit() {
        MockExhibitsRepository.loadExhibits(testContext, "testExhibits.json")
        val exhibit = MockExhibitsRepository.getExhibit("1")
        assertTrue(exhibit is Exhibit)
    }

    @Test
    fun getExhibit_not_existing_returns_null() {
        MockExhibitsRepository.loadExhibits(testContext, "testExhibits.json")
        val exhibit = MockExhibitsRepository.getExhibit("11")
        assertEquals(exhibit, null)
    }
}