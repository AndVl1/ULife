package ru.bmstu.ulife.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bmstu.ulife.main.maps.MapPlacesDummyRepImpl
import ru.bmstu.ulife.main.maps.MapPlacesRepository
import ru.bmstu.ulife.main.maps.MapScreenViewModel
import ru.bmstu.ulife.network.initKtorClient

val appModule = module {
    single<MapPlacesRepository> { MapPlacesDummyRepImpl() }
    viewModel { MapScreenViewModel(get()) }
    single { initKtorClient() }
}
