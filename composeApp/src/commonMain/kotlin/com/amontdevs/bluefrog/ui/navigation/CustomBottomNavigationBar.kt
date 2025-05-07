package com.amontdevs.bluefrog.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_bar_chart
import bluefrog.composeapp.generated.resources.ic_group
import bluefrog.composeapp.generated.resources.ic_home
import bluefrog.composeapp.generated.resources.ic_user
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomBottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedItem: BottomNavigationItem,
    onItemSelected: (BottomNavigationItem) -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    selectedItemColor: Color = MaterialTheme.colorScheme.primary,
    unselectedItemColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    cornerRadius: Int = 24, // Corner radius in dp
) {
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = cornerRadius.dp, topEnd = cornerRadius.dp)),
        color = backgroundColor,
        shadowElevation = 8.dp, // Optional: add some shadow
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            // Padding inside the bar
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomNavigationItem.entries.forEach { item ->
                CustomBottomNavigationItem(
                    item = item,
                    isSelected = item == selectedItem,
                    selectedColor = selectedItemColor,
                    unselectedColor = unselectedItemColor,
                    onClick = { onItemSelected(item) },
                )
            }
        }
    }
}

@Composable
private fun CustomBottomNavigationItem(
    item: BottomNavigationItem,
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit,
) {
    val contentColor = if (isSelected) selectedColor else unselectedColor

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        val icon =
            when (item) {
                BottomNavigationItem.HOME -> Res.drawable.ic_home
                BottomNavigationItem.STATS -> Res.drawable.ic_bar_chart
                BottomNavigationItem.SOCIAL -> Res.drawable.ic_group
                BottomNavigationItem.USER -> Res.drawable.ic_user
            }

        Icon(
            painter = painterResource(icon),
            contentDescription = item.name,
            modifier = Modifier.size(24.dp),
            tint = contentColor,
        )
        val itemText =
            when (item) {
                BottomNavigationItem.HOME -> "Home"
                BottomNavigationItem.STATS -> "Stats"
                BottomNavigationItem.SOCIAL -> "Friends"
                BottomNavigationItem.USER -> "User"
            }

        Text(
            color = contentColor,
            text = itemText,
            fontSize = 12.sp,
        )
    }
}

@Composable
@Preview
fun CustomBottomNavigationBarPreview() {
    BlueFrogTheme(
        darkTheme = true,
    ) {
        CustomBottomNavigationBar(
            selectedItem = BottomNavigationItem.HOME,
            onItemSelected = {},
        )
    }
}

