package de.leoruland.infovisapp.app

import android.app.Application
import de.leoruland.infovisapp.koin.mainModule
import de.leoruland.infovisapp.model.MockExhibitsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class InfovisApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@InfovisApplication)
            modules(mainModule)
        }

        MockExhibitsRepository.loadExhibits(this)
        MockExhibitsRepository.loadTopics(this)
    }
}