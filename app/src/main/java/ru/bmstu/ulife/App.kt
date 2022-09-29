package ru.bmstu.ulife

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.bmstu.ulife.di.appModule
import ru.bmstu.ulife.di.createEventModule
import ru.bmstu.ulife.di.eventsListModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            modules(appModule, eventsListModule, createEventModule)
            androidContext(this@App)
        }
    }
}
