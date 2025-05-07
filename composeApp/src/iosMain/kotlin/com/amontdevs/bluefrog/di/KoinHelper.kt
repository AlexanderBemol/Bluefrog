package com.amontdevs.bluefrog.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            listOf(
                iOSSourceModule,
                repositoryModule,
                viewModelModule,
            ),
        )
    }
}
