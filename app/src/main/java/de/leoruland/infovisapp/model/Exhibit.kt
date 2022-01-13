package de.leoruland.infovisapp.model

import org.osmdroid.util.GeoPoint

data class Exhibit(
    val id: String,
    val name: String,
    val location: GeoPoint,
    val description: String,
    val topics: List<Topic>
)