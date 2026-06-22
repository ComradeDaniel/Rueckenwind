package com.windrad.ui.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

@Composable
expect fun GlassySurface(
    modifier: Modifier = Modifier,
    shape: Shape,
    cornerRadius: Dp,
    color: Color,
    border: BorderStroke? = null,
    content: @Composable BoxScope.() -> Unit
)
