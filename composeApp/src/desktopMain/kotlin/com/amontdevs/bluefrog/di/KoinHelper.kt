package com.amontdevs.bluefrog.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            listOf(
                clientModule,
                sourceModule,
                desktopSourceModule,
                repositoryModule,
                viewModelModule,
                utilsModule,
            ),
        )
    }
}