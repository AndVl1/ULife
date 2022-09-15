package ru.bmstu.ulife.di

import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bmstu.ulife.main.maps.LocationTrackingDataSource
import ru.bmstu.ulife.main.maps.MapPlacesDummyRepImpl
import ru.bmstu.ulife.main.maps.MapPlacesRepository
import ru.bmstu.ulife.main.maps.MapScreenViewModel
import ru.bmstu.ulife.network.initKtorClient

val appModule = module {
    single { initKtorClient() }
}

val mapModule = module {
    single<MapPlacesRepository> { MapPlacesDummyRepImpl() }
    single { LocationServices.getFusedLocationProviderClient(androidContext()) }
    single { LocationTrackingDataSource(get(), get()) }
    viewModel { MapScreenViewModel(get(), get() ) }
}
