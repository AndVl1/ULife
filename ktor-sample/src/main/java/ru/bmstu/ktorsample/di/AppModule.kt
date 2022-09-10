package ru.bmstu.ktorsample.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bmstu.ktorsample.main.input.InputRepository
import ru.bmstu.ktorsample.main.input.InputViewModel
import ru.bmstu.ktorsample.network.EvenService
import ru.bmstu.ktorsample.network.EvenServiceImpl
import ru.bmstu.ktorsample.network.initKtorClient

// should be divided into more for more complicated apps
val appModule = module {
    single { initKtorClient() }
    single<EvenService> { EvenServiceImpl(get()) }
    single { InputRepository(get()) }
    viewModel { InputViewModel(get()) }
}
