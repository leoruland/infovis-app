package de.leoruland.infovisapp.states

import de.leoruland.infovisapp.model.Topic

object TopicsChoiceStateHolder {
    private var topics: List<Topic> = listOf()

    fun getTopics(): List<Topic> = topics

    fun addTopic(topic: Topic) {
        val tempTopics = if (topics.isNotEmpty()) topics as MutableList<Topic> else mutableListOf()
        tempTopics.add(topic)
        topics = tempTopics
    }

    fun removeTopic(topic: Topic): Boolean {
        val tempTopics = if (topics.isNotEmpty()) topics as MutableList<Topic> else mutableListOf()
        return if (tempTopics.contains(topic)) {
            tempTopics.remove(topic)
            true
        } else false
    }

    fun isEmpty(): Boolean = topics.isEmpty()

    fun clearStore() {
        topics = listOf()
    }
}