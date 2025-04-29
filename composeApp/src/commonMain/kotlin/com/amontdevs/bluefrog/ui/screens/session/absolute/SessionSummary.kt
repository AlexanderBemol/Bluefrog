package com.amontdevs.bluefrog.ui.screens.session.absolute

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.bluefrog_report
import com.amontdevs.bluefrog.domain.AbsoluteNote
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Sesión Completada!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Aquí tienes un resumen de tus estadisticas",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        Image(
            modifier = Modifier.width(180.dp),
            painter = painterResource(Res.drawable.bluefrog_report),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )

        StatsCard(
            summaryState = summaryState
        )

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
fun StatsCard(
    summaryState: SessionSummaryState
) {
    val minutes = summaryState.sessionTime.inWholeMinutes
    val remainingSeconds = summaryState.sessionTime.inWholeSeconds % 60
    OutlinedCard {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Respuestas Correctas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${summaryState.correctAnswers}/${summaryState.totalQuestions}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Tiempo Total",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "$minutes mins $remainingSeconds segs",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(Modifier.height(8.dp))
                }
                VerticalDivider(modifier = Modifier.fillMaxHeight() )
                val percentage = summaryState.correctAnswers / summaryState.totalQuestions.toFloat()
                CirclePercentageIndicator(percentageScore = percentage)
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier.width(IntrinsicSize.Max)
            ) {
                Text(
                    text = "Resumen de tus respuestas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                summaryState.answersSummary.forEach {
                    AbsoluteNoteSummaryRow(it)
                }
            }


        }
    }
}

@Composable
fun CirclePercentageIndicator(
    modifier: Modifier = Modifier,
    percentageScore: Float
) {
    val size = 100.dp
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        val initialProgress = if(LocalInspectionMode.current) percentageScore else 0f
        var progress by remember { mutableStateOf(initialProgress) }
        val animatedScore by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1500, easing = EaseInOut),
            label = "Score Circle Animation"
        )
        CircularProgressIndicator(
            progress = { animatedScore },
            modifier = Modifier.size(size),
            strokeWidth = 8.dp,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        Text(
            text = "${(animatedScore * 100).toInt()}%",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        LaunchedEffect(LocalLifecycleOwner.current) {
            progress = percentageScore
        }
    }
}

@Composable
fun AbsoluteNoteSummaryRow(
    noteSummary: AbsoluteSessionSummaryQuestion
) {
    val rowText = buildAnnotatedString {
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("${noteSummary.absoluteNote}: ")
        pop()
        append("${noteSummary.successPercentage}%")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
        ) {
            Icon(
                imageVector =
                    if(noteSummary.answerLevel != SummaryAnswerLevel.NEEDS_IMPROVEMENT) Icons.Rounded.CheckCircle
                            else Icons.Rounded.Warning,
                modifier = Modifier.size(24.dp),
                contentDescription = "Check Circle Icon",
                tint = if(noteSummary.answerLevel != SummaryAnswerLevel.NEEDS_IMPROVEMENT) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.tertiary
            )
        }
        Spacer(Modifier.size(8.dp))
        Text(
            text = rowText,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal
        )
    }
    Spacer(Modifier.size(4.dp))
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
                        correctAnswers = 6,
                        totalQuestions = 10,
                        answersSummary = listOf(
                            AbsoluteSessionSummaryQuestion(AbsoluteNote.C3,3,5),
                            AbsoluteSessionSummaryQuestion(AbsoluteNote.D3,3,8),
                        )
                    ),
                    onContinueClick = {}
                )
            }
        }
    }
}