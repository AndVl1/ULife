package ru.bmstu.ktorsocket.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bmstu.ktorsocket.content.data.VectorsViewModel
import ru.bmstu.ktorsocket.netw.initKtorClient

val appModule = module {
    single { initKtorClient() }
    viewModel { VectorsViewModel(get()) }
}
