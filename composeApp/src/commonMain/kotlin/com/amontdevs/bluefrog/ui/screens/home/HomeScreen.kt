package com.amontdevs.bluefrog.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ill_frog_face
import bluefrog.composeapp.generated.resources.ill_manual_mode
import bluefrog.composeapp.generated.resources.ill_smart_mode
import com.amontdevs.bluefrog.ui.navigation.BottomNavigationItem
import com.amontdevs.bluefrog.ui.navigation.CustomBottomNavigationBar
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        WelcomeHeader()
        Spacer(Modifier.size(32.dp))
        ModeCard(
            title = "Smart Mode",
            bodyText = "Let us guide you trough your learning",
            image = Res.drawable.ill_smart_mode,
        ) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors =
                    ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.outline,
                    ),
            ) {
                Text(
                    text = "Start",
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        ModeCard(
            title = "Manual Mode",
            bodyText = "Create your own custom learning sessions",
            image = Res.drawable.ill_manual_mode,
        ) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors =
                    ButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.outline,
                    ),
            ) {
                Text(
                    text = "Start",
                )
            }
        }
    }
}

@Composable
fun WelcomeHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "Welcome",
                fontSize = 20.sp,
            )
            Text(
                text = "Alexander M.",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        }
        Image(
            painter = painterResource(Res.drawable.ill_frog_face),
            contentDescription = "",
            modifier =
                Modifier
                    .size(70.dp)
                    .border(
                        border = BorderStroke(4.dp, MaterialTheme.colorScheme.primary),
                        shape = CircleShape,
                    ),
        )
    }
}

@Composable
fun ModeCard(
    title: String,
    bodyText: String,
    image: DrawableResource,
    button: @Composable RowScope.() -> Unit,
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp).height(IntrinsicSize.Min),
        ) {
            Column(
                modifier = Modifier.weight(4f).fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = bodyText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Spacer(Modifier.height(32.dp))
                Row(content = button)
            }
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    BlueFrogTheme(
        darkTheme = true,
    ) {
        Scaffold(
            bottomBar = {
                CustomBottomNavigationBar(
                    selectedItem = BottomNavigationItem.HOME,
                    onItemSelected = {},
                )
            },
        ) { paddingValues ->
            Surface(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues)
                        .padding(16.dp),
            ) {
                HomeScreen()
            }
        }
    }
}
