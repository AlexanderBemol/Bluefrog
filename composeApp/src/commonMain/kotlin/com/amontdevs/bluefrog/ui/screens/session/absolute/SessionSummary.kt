package com.amontdevs.bluefrog.ui.screens.session.absolute

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.bluefrog_report
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SessionSummary(
    modifier: Modifier = Modifier,
    summaryState: SessionSummaryState,
    onContinueClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Sesión Completada",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )

        Image(
            modifier = Modifier.width(200.dp),
            painter = painterResource(Res.drawable.bluefrog_report),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(120.dp)
        ) {
            var progress by remember { mutableStateOf(0F) }
            val animatedScore by animateFloatAsState(
                targetValue = progress,
                animationSpec = tween(durationMillis = 1500, easing = EaseInOut),
                label = "Score Circle Animation"
            )
            CircularProgressIndicator(
                progress = { animatedScore },
                modifier = Modifier.size(120.dp),
                strokeWidth = 8.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Text(
                text = "${(animatedScore * 100).toInt()}%",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            LaunchedEffect(LocalLifecycleOwner.current) {
                progress = summaryState.score
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onContinueClick,
            content = {
                Text(
                    text = "Continuar",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )
    }
}

@Composable
@Preview
fun AbsoluteSoundScreenPreview() {
    BlueFrogTheme(
        darkTheme = false
    ) {
        Scaffold { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(16.dp)
            ){
                SessionSummary(
                    summaryState = SessionSummaryState(
                        score = .7f
                    ),
                    onContinueClick = {}
                )
            }
        }
    }
}