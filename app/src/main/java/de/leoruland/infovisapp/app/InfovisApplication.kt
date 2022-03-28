package de.leoruland.infovisapp.app

import android.app.Application
import de.leoruland.infovisapp.model.MockExhibitsRepository

class InfovisApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MockExhibitsRepository.loadExhibits(this, "exhibits.json")
        MockExhibitsRepository.loadTopics(this, "exhibits.json")
    }
}