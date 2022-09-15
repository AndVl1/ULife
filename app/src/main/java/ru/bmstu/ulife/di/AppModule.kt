package ru.bmstu.ulife.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module
import ru.bmstu.ulife.data.repository.LoginRepository
import ru.bmstu.ulife.data.repository.MapPlacesRepository
import ru.bmstu.ulife.data.repository_impl.MapPlacesDummyRepImpl
import ru.bmstu.ulife.data.utils.SharedPreferencesStorage
import ru.bmstu.ulife.main.maps.MapScreenViewModel
import ru.bmstu.ulife.network.initKtorClient
import ru.bmstu.ulife.network.service.LoginService
import ru.bmstu.ulife.network.service.LoginServiceImpl
import ru.bmstu.ulife.source.LoginRemoteDataSource
import ru.bmstu.ulife.view_models.LoginViewModel

val appModule = module {
    single<MapPlacesRepository> { MapPlacesDummyRepImpl() }
    viewModel { MapScreenViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    factory { SharedPreferencesStorage(get()) }
    single{ getSharedPrefs(androidApplication()) }
    single { LoginServiceImpl() }
    //single { LoginRemoteDataSource(get()) } bind LoginRemoteDataSource::class
    single { LoginRepository(SharedPreferencesStorage(get()), LoginRemoteDataSource(get())) }
    single { initKtorClient() }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("prefs",  Context.MODE_PRIVATE)
}