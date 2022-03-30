package de.leoruland.infovisapp.repository

import android.content.Context
import de.leoruland.infovisapp.data.Exhibit
import de.leoruland.infovisapp.data.Topic

interface ExhibitsRepository {
    fun getTopics(): List<Topic>
    fun getExhibits(topics: List<Topic>?, listInclusive: Boolean = true): List<Exhibit>
    fun getExhibit(id: String): Exhibit?
    fun loadExhibits(context: Context, filename: String)
    fun loadTopics(context: Context, filename: String)
}