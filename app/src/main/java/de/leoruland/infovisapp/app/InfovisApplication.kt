package de.leoruland.infovisapp.app

import android.app.Application
import de.leoruland.infovisapp.model.TopicStore

class InfovisApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        TopicStore.loadTopics(this)
    }
}