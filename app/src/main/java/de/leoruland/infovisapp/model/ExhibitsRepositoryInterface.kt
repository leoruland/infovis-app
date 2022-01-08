package de.leoruland.infovisapp.model

import android.content.Context

interface ExhibitsRepositoryInterface {
    fun getTopics(): List<Topic>
    fun getExhibits(topics: List<Topic>?, listInclusive: Boolean = true): List<Exhibit>
    fun getExhibit(id: String): Exhibit?
    fun loadExhibits(context: Context)
    fun loadTopics(context: Context)
}