package de.leoruland.infovisapp.model

import com.google.gson.annotations.SerializedName
import org.osmdroid.util.GeoPoint

data class Exhibit(
    val id: String,
    val number: String,
    val name: String,
    @SerializedName("image_link") val imageLink: String,
    val location: GeoPoint,
    val description: String,
    val topics: List<Topic>
)