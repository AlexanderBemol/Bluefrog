package com.amontdevs.bluefrog

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.amontdevs.bluefrog.di.initKoin

fun main() =
    application {
        initKoin()
        Window(
            onCloseRequest = ::exitApplication,
            title = "Bluefrog",
        ) {
            App()
        }
    }
