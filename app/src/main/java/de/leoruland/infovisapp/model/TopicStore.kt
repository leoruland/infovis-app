package de.leoruland.infovisapp.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object TopicStore {
    private const val TAG = "TopicStore"

    private lateinit var topics: List<String>

    fun getTopics() = topics

    fun loadTopics(context: Context) {
        val gson = Gson()
        val json = loadJSONFromAsset("exhibits.json", context)
        val listType = object : TypeToken<List<Exhibit>>() {}.type
        val exhibits: List<Exhibit> = gson.fromJson(
            json,
            listType
        )

        val tempTopics: MutableList<String> = mutableListOf()
        exhibits.map { exhibit ->
            exhibit.topics.forEach { topic ->
                if (!tempTopics.contains(topic))
                    tempTopics.add(topic)
            }
        }

        topics = tempTopics
        Log.v(TAG, "Found ${topics.size} topic items")
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

}