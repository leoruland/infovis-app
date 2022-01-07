package de.leoruland.infovisapp.app

import android.app.Application
import de.leoruland.infovisapp.model.ExhibitsRepository

class InfovisApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ExhibitsRepository.loadExhibits(this)
        ExhibitsRepository.loadTopics(this)
    }
}