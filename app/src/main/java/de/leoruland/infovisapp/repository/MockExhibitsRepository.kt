package de.leoruland.infovisapp.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.leoruland.infovisapp.data.Exhibit
import de.leoruland.infovisapp.data.Topic
import java.io.IOException

object MockExhibitsRepository : ExhibitsRepository {
    private const val TAG = "ExhibitsStore"

    private lateinit var exhibits: List<Exhibit>
    private lateinit var topics: List<Topic>

    override fun loadExhibits(context: Context, filename: String) {
        val gson = Gson()
        val json = loadJSONFromAsset(filename, context)
        val listType = object : TypeToken<List<Exhibit>>() {}.type
        exhibits = gson.fromJson(json, listType)
    }

    override fun loadTopics(context: Context, filename: String) {
        val gson = Gson()
        val json = loadJSONFromAsset(filename, context)
        val listType = object : TypeToken<List<Exhibit>>() {}.type
        val exhibits: List<Exhibit> = gson.fromJson(
            json,
            listType
        )

        val topics: MutableList<Topic> = mutableListOf()
        exhibits.map { exhibit ->
            exhibit.topics.forEach { topic ->
                if (!topics.contains(topic))
                    topics.add(topic)
            }
        }

        this.topics = topics
    }

    /**
     * Code von
     * https://stackoverflow.com/questions/56962608/how-to-read-json-file-from-assests-in-android-using-kotlin
     * am 10.01.2022
     */
    private fun loadJSONFromAsset(filename: String, context: Context): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (ex: IOException) {
            Log.e(TAG, "Error reading from $filename", ex)
        }
        return json
    }

    override fun getTopics(): List<Topic> {
        return topics
    }

    override fun getExhibits(topics: List<Topic>?, listInclusive: Boolean): List<Exhibit> {
        return if (topics != null) {
            val exhibits = mutableListOf<Exhibit>()
            this.exhibits.map { exhibit ->
                if (listInclusive) {
                    exhibit.topics.forEach { topic ->
                        if (topics.contains(topic) && !exhibits.contains(exhibit))
                            exhibits.add(exhibit)
                    }
                } else if (exhibit.topics.containsAll(topics) && !exhibits.contains(exhibit))
                    exhibits.add(exhibit)
            }
            exhibits
        } else emptyList()
    }

    override fun getExhibit(number: String): Exhibit? {
        exhibits.forEach { if (it.number == number) return it }
        return null
    }
}