package com.amontdevs.bluefrog.di

import com.amontdevs.bluefrog.utils.NetworkConnectivityHelper
import org.koin.dsl.module

actual val utilsModule = module {
    single { NetworkConnectivityHelper(get()) }
}