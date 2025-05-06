package com.amontdevs.bluefrog.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import kotlinx.coroutines.launch

@Composable
fun PrimaryIconButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,
    icon: @Composable (RowScope.() -> Unit) = {},
) {
    Button(
        colors = colors,
        onClick = onClick,
        shape = CircleShape,
        modifier =
            modifier
                .size(150.dp),
        content = icon,
    )
}

fun Modifier.bottomShadow(
    height: Dp = 4.dp,
    shadowColor: Color = Color.Black.copy(alpha = 0.5f),
): Modifier =
    this
        .clip(RoundedCornerShape(16.dp))
        .drawWithContent {
            drawContent()

            val shadowHeight = height.toPx()
            val shadowWidth = size.width
            val semiTransparent = shadowColor.copy(alpha = 0.7f)
            val colors = listOf(Color.Transparent, semiTransparent, shadowColor)

            drawRoundRect(
                brush =
                    Brush.verticalGradient(
                        colors = colors,
                        startY = size.height - shadowHeight,
                        endY = size.height,
                    ),
                topLeft = Offset(0f, 0f),
                size = size,
            )
        }

fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)
    val paint = Paint()
    // Apply specified color
    paint.color = color
    // Check for valid blur radius
    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            // maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        // Save the canvas state
        canvas.save()
        // Translate to specified offsets
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        // Draw the shadow
        canvas.drawOutline(shadowOutline, paint)
        // Restore the canvas state
        canvas.restore()
    }
}

fun Modifier.animatePlacement(): Modifier =
    composed {
        val scope = rememberCoroutineScope()
        var targetOffset by remember { mutableStateOf(IntOffset.Zero) }
        var animatable by remember {
            mutableStateOf<Animatable<IntOffset, AnimationVector2D>?>(null)
        }
        this
            .onPlaced {
                // Calculate the position in the parent layout
                targetOffset = it.positionInParent().round()
            }.offset {
                // Animate to the new target offset when alignment changes.
                val anim =
                    animatable
                        ?: Animatable(targetOffset, IntOffset.VectorConverter).also {
                            animatable = it
                        }
                if (anim.targetValue != targetOffset) {
                    scope.launch {
                        anim.animateTo(targetOffset, spring(stiffness = StiffnessMediumLow))
                    }
                }
                // Offset the child in the opposite direction to the targetOffset, and slowly catch
                // up to zero offset via an animation to achieve an overall animated movement.
                animatable?.let { it.value - targetOffset } ?: IntOffset.Zero
            }
    }
