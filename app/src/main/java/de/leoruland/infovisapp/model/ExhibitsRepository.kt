package de.leoruland.infovisapp.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object ExhibitsRepository : ExhibitsRepositoryInterface {
    private const val TAG = "ExhibitsStore"

    private lateinit var exhibits: List<Exhibit>
    private lateinit var topics: List<Topic>

    override fun loadExhibits(context: Context) {
        val gson = Gson()
        val json = loadJSONFromAsset("exhibits.json", context)
        val listType = object : TypeToken<List<Exhibit>>() {}.type
        exhibits = gson.fromJson(json, listType)
    }

    override fun loadTopics(context: Context) {
        val gson = Gson()
        val json = loadJSONFromAsset("exhibits.json", context)
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
     * Von Raywenderlich.com
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
//        loadTopics() // TODO insert context
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
        } else exhibits
    }

    override fun getExhibit(id: String): Exhibit? {
        exhibits.forEach { if (it.id == id) return it }
        return null
    }
}