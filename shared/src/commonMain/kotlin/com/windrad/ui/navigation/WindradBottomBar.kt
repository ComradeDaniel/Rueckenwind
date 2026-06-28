package com.windrad.ui.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.theme.AppColors

import com.windrad.ui.map.GlassySurface
import androidx.compose.ui.draw.shadow
import org.jetbrains.compose.resources.painterResource


@Composable
fun WindradBottomBar(
    currentDestination: WindradDestination,
    onDestinationSelected: (WindradDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val barModifier = modifier
        .widthIn(max = 380.dp)
        .fillMaxWidth()
        .requiredHeight(64.dp)
        .shadow(
            elevation = 4.dp,
            shape = RoundedCornerShape(32.dp),
            clip = false
        )

    val contentBlock = @Composable {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WindradDestination.entries.forEach { destination ->
                val selected = currentDestination == destination
                val contentColor = if (selected) MaterialTheme.colorScheme.primary else AppColors.DarkIcon
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(all = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = if (selected) AppColors.SelectedItemOverlay else Color.Transparent,
                        shape = RoundedCornerShape(percent = 50)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { onDestinationSelected(destination) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                val iconModifier = if (destination == WindradDestination.COLLECTION) {
                                    Modifier.size(width = 34.dp, height = 22.dp)
                                } else {
                                    Modifier.size(24.dp)
                                }
                                Icon(
                                    painter = painterResource(destination.icon),
                                    contentDescription = destination.label,
                                    tint = contentColor,
                                    modifier = iconModifier
                                )
                                Text(
                                    text = destination.label,
                                    color = contentColor,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    Surface(
        modifier = barModifier,
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
    ) {
        contentBlock()
    }
}
