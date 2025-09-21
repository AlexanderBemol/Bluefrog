package com.amontdevs.bluefrog.ui.dialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class CustomToast(
    val title: String = "",
    val message: String = "",
    val duration: ToastDuration = ToastDuration.SHORT,
    val kindOfToast: KindOfToast = KindOfToast.Success,
) {
    companion object {
        const val SHORT_DURATION = 2000L
        const val LONG_DURATION = 5000L

        enum class ToastDuration(
            val value: Long,
        ) {
            SHORT(SHORT_DURATION),
            LONG(LONG_DURATION),
        }
    }
}

enum class KindOfToast {
    Error,
    Success,
    Info,
    Warning,
    ;

    @Composable
    fun getBackgroundColor(): Color =
        when (this) {
            Error -> MaterialTheme.colorScheme.errorContainer
            Info -> MaterialTheme.colorScheme.primaryContainer
            Success -> MaterialTheme.colorScheme.secondaryContainer
            Warning -> MaterialTheme.colorScheme.tertiaryContainer
        }

    @Composable
    fun getContentColor(): Color =
        when (this) {
            Error -> MaterialTheme.colorScheme.onErrorContainer
            Info -> MaterialTheme.colorScheme.onPrimaryContainer
            Success -> MaterialTheme.colorScheme.onSecondaryContainer
            Warning -> MaterialTheme.colorScheme.onTertiaryContainer
        }
}
