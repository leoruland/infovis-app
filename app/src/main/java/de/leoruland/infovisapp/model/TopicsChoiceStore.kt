package de.leoruland.infovisapp.model

object TopicsChoiceStore {
    private var topics: List<String> = listOf()

    fun getTopics(): List<String> = topics

    fun addTopic(topic: String) {
        val tempTopics = if (topics.isNotEmpty()) topics as MutableList<String> else mutableListOf()
        tempTopics.add(topic)
        topics = tempTopics
    }

    fun removeTopic(topic: String): Boolean {
        val tempTopics = if (topics.isNotEmpty()) topics as MutableList<String> else mutableListOf()
        return if (tempTopics.contains(topic)) {
            tempTopics.remove(topic)
            true
        } else false
    }
}