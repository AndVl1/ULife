package ru.bmstu.ulife.di

import org.koin.dsl.module
import ru.bmstu.ulife.network.initKtorClient

val appModule = module {
    single { initKtorClient() }
}
