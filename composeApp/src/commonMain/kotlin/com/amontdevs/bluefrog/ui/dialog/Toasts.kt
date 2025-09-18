package com.amontdevs.bluefrog.ui.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.amontdevs.bluefrog.ui.theme.P0
import com.amontdevs.bluefrog.ui.theme.P2
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.utils.ContentPreview
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomToast(
    modifier: Modifier = Modifier,
    toastVisible: Boolean = false,
    customToast: CustomToast,
    onDismiss: () -> Unit = {},
) {
    LaunchedEffect(toastVisible) {
        if (toastVisible) {
            delay(customToast.duration.value)
            onDismiss()
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        AnimatedVisibility(
            visible = toastVisible,
            enter =
                fadeIn() +
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                    ),
            exit =
                fadeOut() +
                    slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight },
                    ),
        ) {
            val contentColor = customToast.kindOfToast.getContentColor()
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = P3),
                colors =
                    CardDefaults.elevatedCardColors(
                        containerColor = customToast.kindOfToast.getBackgroundColor(),
                    ),
            ) {
                Column(
                    modifier = Modifier.Companion.padding(P2),
                ) {
                    Text(
                        text = customToast.title,
                        color = contentColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.Companion.height(P0))
                    Text(
                        text = customToast.message,
                        color = contentColor,
                        fontSize = 16.sp,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        TextButton(
                            onClick = onDismiss,
                        ) {
                            Text(
                                text = "Dismiss",
                                color = contentColor,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun CustomToastPreview() {
    ContentPreview {
        CustomToast(
            toastVisible = true,
            customToast =
                CustomToast(
                    title = "Error",
                    message = "This is an error message",
                    duration = CustomToast.Companion.ToastDuration.SHORT,
                    kindOfToast = KindOfToast.Success,
                ),
            onDismiss = {},
        )
    }
}
