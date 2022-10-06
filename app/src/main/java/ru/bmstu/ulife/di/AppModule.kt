package ru.bmstu.ulife.di

import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.bmstu.ulife.data.repository.EventDetailRepository
import ru.bmstu.ulife.main.create.data.CreateEventRepository
import ru.bmstu.ulife.main.create.data.CreateEventViewModel
import ru.bmstu.ulife.main.create.data.ImageUploadService
import ru.bmstu.ulife.data.repository.LoginRepository
import ru.bmstu.ulife.data.repository.MapPlacesRepository
import ru.bmstu.ulife.main.events.list.EventsListViewModel
import ru.bmstu.ulife.utils.SharedPreferencesStorage
import ru.bmstu.ulife.main.events.maps.LocationTrackingDataSource
import ru.bmstu.ulife.main.events.maps.MapPlacesRepImpl
import ru.bmstu.ulife.main.events.maps.MapScreenViewModel
import ru.bmstu.ulife.network.initKtorClient
import ru.bmstu.ulife.network.service.EventDetailService
import ru.bmstu.ulife.network.service.EventDetailServiceImpl
import ru.bmstu.ulife.network.service.LoginServiceImpl
import ru.bmstu.ulife.source.EventDetailRemoteDataSource
import ru.bmstu.ulife.source.LoginRemoteDataSource
import ru.bmstu.ulife.view_models.EventViewModel
import ru.bmstu.ulife.view_models.LoginViewModel

val appModule = module {
    single { initKtorClient() }
    single(named("storage")) { SharedPreferencesStorage(get()) }
}

val loginModule = module {
    single { LoginServiceImpl() }
    single { LoginRepository(SharedPreferencesStorage(get()), LoginRemoteDataSource(get())) }
    viewModel { LoginViewModel(get()) }
}

val eventsListModule = module {
    single<MapPlacesRepository> { MapPlacesRepImpl(get(), get()) }
    single { LocationServices.getFusedLocationProviderClient(androidContext()) }
    single { LocationTrackingDataSource(get(), get()) }
    viewModel { MapScreenViewModel(get(), get() ) }
    viewModel { EventsListViewModel(get()) }
}

val eventDetailModule = module {
    single<EventDetailService> { EventDetailServiceImpl() }
    single { EventDetailRepository(SharedPreferencesStorage(get()), EventDetailRemoteDataSource(get())) }
    viewModel { EventViewModel(get()) }
}

val createEventModule = module {
    viewModel { CreateEventViewModel(get()) }
    single { ImageUploadService(get()) }
    single { CreateEventRepository(get(), get(), get(), androidContext()) }
}
