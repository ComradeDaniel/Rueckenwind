package com.windrad.ui.collection

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CollectionScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "📦",
                fontSize = 48.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
                        Text(
                text = "Sammlung",
                fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
                        )
            Spacer(modifier = Modifier.height(8.dp))
                        Text(
                text = "Deine gescannten Windparks erscheinen hier.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                }
            }
