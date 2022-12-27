package ru.bmstu.ktorsocket

import android.app.Application
import org.koin.core.context.startKoin
import ru.bmstu.ktorsocket.di.appModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}