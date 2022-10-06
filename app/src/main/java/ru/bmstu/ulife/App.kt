package ru.bmstu.ulife

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.bmstu.ulife.di.*

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            modules(appModule, eventsListModule, createEventModule, loginModule, eventDetailModule)
            androidContext(this@App)
        }
    }
}
