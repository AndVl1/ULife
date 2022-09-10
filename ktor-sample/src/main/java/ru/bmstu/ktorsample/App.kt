package ru.bmstu.ktorsample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.bmstu.ktorsample.di.appModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
            androidContext(this@App)
        }
    }
}
