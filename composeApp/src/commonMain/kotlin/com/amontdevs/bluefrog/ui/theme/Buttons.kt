package com.amontdevs.bluefrog.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_apple
import bluefrog.composeapp.generated.resources.ic_google
import org.jetbrains.compose.resources.painterResource

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

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    addIcon: Boolean = false,
    icon: @Composable (RowScope.() -> Unit) = {},
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(P1),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (addIcon) {
                icon()
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
            )
        }
    }
}

@Composable
fun PrimaryOutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
        )
    }
}

@Composable
fun OnSurfaceOutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    addIcon: Boolean = false,
    icon: @Composable (RowScope.() -> Unit) = {},
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurfaceVariant),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(P1),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (addIcon) {
                icon()
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun AppleButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val buttonColor = LocalCustomThemeColors.current.appleColor

    Button(
        onClick = onClick,
        border = BorderStroke(2.dp, buttonColor),
        modifier = modifier,
        colors =
            ButtonDefaults.buttonColors().copy(
                containerColor = buttonColor,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(P1),
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(resource = Res.drawable.ic_apple),
                contentDescription = "Apple Icon",
                tint = LocalCustomThemeColors.current.appleContentColor,
                modifier = Modifier.size(24.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = LocalCustomThemeColors.current.appleContentColor,
            )
        }
    }
}

@Composable
fun GoogleButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val googleGradientBrush =
        Brush.linearGradient(
            colors =
                listOf(
                    GoogleRed,
                    GoogleYellow,
                    GoogleGreen,
                    GoogleBlue,
                    GoogleRed,
                ),
        )

    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(2.dp, googleGradientBrush),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(P1), // Assuming P1 is defined
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(resource = Res.drawable.ic_google), // Assuming Res is available
                contentDescription = "Google Icon",
                modifier = Modifier.size(24.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
fun PrimaryTextButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
        )
    }
}
