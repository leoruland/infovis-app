package de.leoruland.infovisapp.repository

import org.osmdroid.util.GeoPoint

data class Exhibit(
    val id: String,
    val number: String,
    val name: String,
    val repository: String,
    val location: GeoPoint,
    val description: String,
    val topics: List<Topic>
)