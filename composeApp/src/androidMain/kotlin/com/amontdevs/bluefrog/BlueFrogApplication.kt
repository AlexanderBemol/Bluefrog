package com.amontdevs.bluefrog

import android.app.Application
import com.amontdevs.bluefrog.di.androidSourceModule
import com.amontdevs.bluefrog.di.clientModule
import com.amontdevs.bluefrog.di.repositoryModule
import com.amontdevs.bluefrog.di.sourceModule
import com.amontdevs.bluefrog.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BlueFrogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BlueFrogApplication)
            modules(
                listOf(
                    androidSourceModule,
                    clientModule,
                    sourceModule,
                    repositoryModule,
                    viewModelModule,
                ),
            )
        }
    }
}
