package de.leoruland.infovisapp.states

import de.leoruland.infovisapp.data.Topic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TopicsChoiceStateHolderTest {

    private val firstSampleTopic = Topic("Erstes")
    private val secondSampleTopic = Topic("Zweites")

    @After
    fun tearDown() {
        TopicsChoiceStateHolder.clearStore()
    }

    @Test
    fun getTopics_returns_correct_topics() {
        TopicsChoiceStateHolder.addTopic(firstSampleTopic)
        assertTrue(TopicsChoiceStateHolder.getTopics().contains(firstSampleTopic))
    }

    @Test
    fun getTopics_no_topics_returns_empty_list() {
        assertEquals(TopicsChoiceStateHolder.getTopics(), listOf<Topic>())
    }


    @Test
    fun addTopic_adds_two_topics_successfully() {
        TopicsChoiceStateHolder.addTopic(firstSampleTopic)
        TopicsChoiceStateHolder.addTopic(secondSampleTopic)
        assertTrue(TopicsChoiceStateHolder.getTopics().contains(firstSampleTopic))
        assertTrue(TopicsChoiceStateHolder.getTopics().contains(secondSampleTopic))
    }

    @Test
    fun removeTopic_removes_topic_successfully() {
        TopicsChoiceStateHolder.addTopic(firstSampleTopic)
        TopicsChoiceStateHolder.addTopic(secondSampleTopic)
        TopicsChoiceStateHolder.removeTopic(firstSampleTopic)
        assertFalse(TopicsChoiceStateHolder.getTopics().contains(firstSampleTopic))
        assertTrue(TopicsChoiceStateHolder.getTopics().contains(secondSampleTopic))
    }

    @Test
    fun isEmpty_returns_true_on_empty() {
        assertTrue(TopicsChoiceStateHolder.isEmpty())
    }

    @Test
    fun isEmpty_returns_false_on_not_empty() {
        TopicsChoiceStateHolder.addTopic(firstSampleTopic)
        assertFalse(TopicsChoiceStateHolder.isEmpty())
    }

    @Test
    fun clearStore_clears_all_entries() {
        TopicsChoiceStateHolder.addTopic(firstSampleTopic)
        TopicsChoiceStateHolder.addTopic(secondSampleTopic)
        TopicsChoiceStateHolder.clearStore()
        assertFalse(TopicsChoiceStateHolder.getTopics().contains(firstSampleTopic))
        assertFalse(TopicsChoiceStateHolder.getTopics().contains(secondSampleTopic))
        assertTrue(TopicsChoiceStateHolder.isEmpty())
    }
}