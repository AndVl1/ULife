package ru.bmstu.ulife

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.bmstu.ulife.di.appModule
import ru.bmstu.ulife.di.createEventModule
import ru.bmstu.ulife.di.eventDetailModule
import ru.bmstu.ulife.di.eventsListModule
import ru.bmstu.ulife.di.loginModule


class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            modules(appModule, eventsListModule, createEventModule, loginModule, eventDetailModule)
            androidContext(this@App)
        }
        MapKitFactory.setApiKey("dcfd28b3-101a-435f-abe2-59870a59847e")
    }
}
