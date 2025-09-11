package com.amontdevs.bluefrog.ui.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.gkey
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GenerateNoteDrawable(
    modifier: Modifier = Modifier,
    canvasWidth: Dp = 140.dp,
    absoluteNote: AbsoluteNote,
    secondAbsoluteNote: AbsoluteNote? = null,
    gridColor: Color = Color.Black,
    gKeyColor: Color = Color.Black,
    noteColor: Color = Color.Black,
) {
    val gKeyPainter = painterResource(resource = Res.drawable.gkey)

    // Apply the canvas size and padding
    val canvasHeight = if (secondAbsoluteNote == null) canvasWidth * 1.4f else canvasWidth * .85f
    val canvasModifier =
        modifier
            .width(canvasWidth)
            .height(canvasHeight)
            .padding(vertical = canvasHeight * 0.03f, horizontal = canvasWidth * 0.1f)

    Canvas(canvasModifier) {
        // Define all drawing constants relative to the canvas size
        val canvasWidth = size.width
        val canvasHeight = size.height

        val gridLineStrokeWidth = canvasHeight * 0.025f
        val noteOvalWidth = canvasWidth * if (secondAbsoluteNote == null) 0.18f else 0.15f
        val noteOvalHeight = canvasHeight * if (secondAbsoluteNote == null) 0.08f else 0.1f
        val noteStemWidth = canvasHeight * 0.045f
        val noteStemLength = canvasHeight * 0.4f
        val sharpSize = canvasHeight * 0.12f

        // Generate the background grid and get the Y positions
        val linesYPositions = generateBackgroundGrid(
            strokeWidth = gridLineStrokeWidth,
            gKeyPainter = gKeyPainter,
            gridColor = gridColor,
            gKeyColor = gKeyColor,
        )

        val firstNoteXPosition = if (secondAbsoluteNote != null) canvasWidth * 0.65f else canvasWidth * 0.85f
        val secondNoteXPosition = canvasWidth * 0.95f

        // Draw the first note
        drawSingleNote(
            absoluteNote = absoluteNote,
            linesYPositions = linesYPositions,
            noteXPosition = firstNoteXPosition,
            noteColor = noteColor,
            gridColor = gridColor,
            noteOvalWidth = noteOvalWidth,
            noteOvalHeight = noteOvalHeight,
            noteStemWidth = noteStemWidth,
            noteStemLength = noteStemLength,
            gridLineStrokeWidth = gridLineStrokeWidth,
            sharpSize = sharpSize,
        )

        // Draw the second note if it exists
        if (secondAbsoluteNote != null) {
            drawSingleNote(
                absoluteNote = secondAbsoluteNote,
                linesYPositions = linesYPositions,
                noteXPosition = secondNoteXPosition,
                noteColor = noteColor,
                gridColor = gridColor,
                noteOvalWidth = noteOvalWidth,
                noteOvalHeight = noteOvalHeight,
                noteStemWidth = noteStemWidth,
                noteStemLength = noteStemLength,
                gridLineStrokeWidth = gridLineStrokeWidth,
                sharpSize = sharpSize,
            )
        }
    }
}

private fun DrawScope.drawSingleNote(
    absoluteNote: AbsoluteNote,
    linesYPositions: List<Float>,
    noteXPosition: Float,
    noteColor: Color,
    gridColor: Color,
    noteOvalWidth: Float,
    noteOvalHeight: Float,
    noteStemWidth: Float,
    noteStemLength: Float,
    gridLineStrokeWidth: Float,
    sharpSize: Float,
) {
    val notePosition = absoluteNote.gKeyPosition
    val noteYPosition = linesYPositions[notePosition]

    // Determine note stem direction and extra lines
    val isUp = notePosition >= 7
    val isExtraUpLineNeeded = notePosition < 2
    val isExtraDownLineNeeded = notePosition > 12

    // Calculate drawing positions relative to the canvas
    val noteLineXPosition = if (isUp) noteXPosition else noteXPosition - noteOvalWidth + noteStemWidth * 0.5f

    // Draw the note stem, which will rotate based on if it's up or down
    val noteLineStartYPosition = if (isUp) noteYPosition else noteYPosition + noteOvalHeight * 0.2f
    val noteLineEndYPosition = noteYPosition - noteStemLength

    // Draw extra ledger lines if needed
    if (isExtraUpLineNeeded || isExtraDownLineNeeded) {
        val middleLineWidth = noteOvalWidth * 1.5f
        val middleLineStartX = noteXPosition - noteOvalWidth * if (isExtraUpLineNeeded) 1.2f else 1f
        val middleLineY =
            if (isExtraDownLineNeeded) {
                linesYPositions[13]
            } else {
                linesYPositions[1]
            }
        val middleLineEndX = middleLineStartX + middleLineWidth
        drawLine(
            color = gridColor,
            start = Offset(middleLineStartX, middleLineY),
            end = Offset(middleLineEndX, middleLineY),
            cap = StrokeCap.Round,
            strokeWidth = gridLineStrokeWidth,
        )
    }

    rotate(
        degrees = if (isUp) 0f else 180f,
        pivot = Offset(noteLineXPosition, noteLineStartYPosition),
    ) {
        drawLine(
            color = noteColor,
            start = Offset(noteLineXPosition, noteLineStartYPosition),
            end = Offset(noteLineXPosition, noteLineEndYPosition),
            cap = StrokeCap.Round,
            strokeWidth = noteStemWidth,
        )
    }

    // Draw the note head (oval)
    val ovalTopLeftX = noteXPosition - noteOvalWidth + noteStemWidth / 2f
    val ovalTopLeftY = noteYPosition - noteStemWidth * 1f
    val ovalPivotX = ovalTopLeftX + noteOvalWidth / 2
    val ovalPivotY = ovalTopLeftY + noteOvalHeight / 2

    rotate(
        degrees = -20f,
        pivot = Offset(ovalPivotX, ovalPivotY),
    ) {
        drawOval(
            color = noteColor,
            topLeft = Offset(ovalTopLeftX, ovalTopLeftY),
            size = Size(noteOvalWidth, noteOvalHeight),
            style = Fill,
        )
    }


    // Draw the sharp symbol if needed
    if (absoluteNote.isSharp) {
        val sharpLineWidth = sharpSize * 0.15f
        val sharpSpaceSize = sharpSize * 0.4f
        val sharpVerticalLineStartY = noteYPosition - sharpSize * 0.25f
        val sharpVerticalLineEndY = sharpVerticalLineStartY + sharpSize

        val sharpFirstVerticalLineStartX = noteXPosition - noteOvalWidth - sharpSpaceSize - if (!isUp) sharpLineWidth * 1.5f else 0f
        drawLine(
            color = noteColor,
            start = Offset(sharpFirstVerticalLineStartX, sharpVerticalLineStartY),
            end = Offset(sharpFirstVerticalLineStartX, sharpVerticalLineEndY),
            cap = StrokeCap.Round,
            strokeWidth = sharpLineWidth,
        )

        val sharpSecondVerticalLineStartX = sharpFirstVerticalLineStartX + sharpSpaceSize
        drawLine(
            color = noteColor,
            start = Offset(sharpSecondVerticalLineStartX, sharpVerticalLineStartY),
            end = Offset(sharpSecondVerticalLineStartX, sharpVerticalLineEndY),
            cap = StrokeCap.Round,
            strokeWidth = sharpLineWidth,
        )

        val sharpHorizontalLineStartX = sharpFirstVerticalLineStartX - sharpSpaceSize * 0.75f
        val sharpHorizontalLineEndX = sharpHorizontalLineStartX + sharpSize
        val sharpHorizontalLineY1 = noteYPosition + sharpLineWidth
        val sharpHorizontalLineY2 = noteYPosition + sharpLineWidth - sharpSize * 0.15f
        val sharpHorizontalLineY3 = sharpHorizontalLineY1 + sharpSpaceSize
        val sharpHorizontalLineY4 = sharpHorizontalLineY2 + sharpSpaceSize

        drawLine(
            color = noteColor,
            start = Offset(sharpHorizontalLineStartX, sharpHorizontalLineY1),
            end = Offset(sharpHorizontalLineEndX, sharpHorizontalLineY2),
            cap = StrokeCap.Round,
            strokeWidth = sharpLineWidth,
        )

        drawLine(
            color = noteColor,
            start = Offset(sharpHorizontalLineStartX, sharpHorizontalLineY3),
            end = Offset(sharpHorizontalLineEndX, sharpHorizontalLineY4),
            cap = StrokeCap.Round,
            strokeWidth = sharpLineWidth,
        )
    }
}

private fun DrawScope.generateBackgroundGrid(
    strokeWidth: Float,
    gKeyPainter: Painter,
    gridColor: Color = Color.Black,
    gKeyColor: Color = Color.Black,
    twoNotes: Boolean = false,
): List<Float> {
    val linesYPositions = mutableListOf<Float>()
    val yLinesOffset = size.height * 0.25f
    val spaceSize = (size.height - (yLinesOffset * 2)) / 4.5f
    var y = yLinesOffset

    // Draw the 5 main grid lines
    repeat(5) {
        linesYPositions.add(y)
        drawLine(
            color = gridColor,
            start = Offset(strokeWidth / 2, y),
            end = Offset((size.width - strokeWidth / 2), y),
            cap = StrokeCap.Round,
            strokeWidth = strokeWidth,
        )
        y += spaceSize + strokeWidth
    }

    with(gKeyPainter) {
        draw(
            size =
                Size(
                    gKeyPainter.intrinsicSize.width * (size.height / gKeyPainter.intrinsicSize.height),
                    size.height,
                ),
            colorFilter = ColorFilter.tint(gKeyColor),
        )
    }

    // Calculate all possible note positions (both on and between lines)
    // Calculate all possible note positions from top to bottom
    val allNotePositions = mutableListOf<Float>()
    // The space between each note position (half a staff space)
    val spaceStep = (linesYPositions[1] - linesYPositions[0]) / 2f
    // The y-position of the top-most ledger line note
    val topMostY = linesYPositions[0] - 3 * spaceStep

    // Generate positions for 15 notes, from the top ledger line down
    for (i in 0 until 15) {
        allNotePositions.add(topMostY + i * spaceStep)
    }

    return allNotePositions
}

@Composable
@Preview
fun GenerateNoteDrawablePreview() {
    GenerateNoteDrawable(
        modifier = Modifier.background(Color.White),
        canvasWidth = 140.dp,
        absoluteNote = AbsoluteNote.C3,
        gridColor = Color.Black,
        noteColor = Color.Black,
        gKeyColor = Color.Black,
    )
}


@Composable
@Preview
fun GenerateTwoNotesDrawablePreview() {
    GenerateNoteDrawable(
        modifier = Modifier.background(Color.White),
        canvasWidth = 140.dp,
        absoluteNote = AbsoluteNote.AS3,
        secondAbsoluteNote = AbsoluteNote.GS3,
        gridColor = Color.Black,
        noteColor = Color.Black,
        gKeyColor = Color.Black,
    )
}
