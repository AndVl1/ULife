package ru.bmstu.ktorsocket.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bmstu.ktorsocket.content.data.PeakViewModel
import ru.bmstu.ktorsocket.netw.initKtorClient

val appModule = module {
    single { initKtorClient() }
    viewModel { PeakViewModel(get()) }
}
