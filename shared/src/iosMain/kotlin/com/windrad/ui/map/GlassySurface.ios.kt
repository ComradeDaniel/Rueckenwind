package com.windrad.ui.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.UIKitView
import platform.UIKit.UIBlurEffect
import platform.UIKit.UIBlurEffectStyle
import platform.UIKit.UIVisualEffectView
import platform.UIKit.UIColor
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun GlassySurface(
    modifier: Modifier,
    shape: Shape,
    cornerRadius: Dp,
    color: Color,
    border: BorderStroke?,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .let { if (border != null) it.border(border, shape) else it }
    ) {
        UIKitView(
            factory = {
                val blurEffect = UIBlurEffect.effectWithStyle(UIBlurEffectStyle.UIBlurEffectStyleLight)
                UIVisualEffectView(effect = blurEffect).apply {
                    backgroundColor = UIColor.clearColor
                    clipsToBounds = true
                    layer.cornerRadius = cornerRadius.value.toDouble()
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                view.clipsToBounds = true
                view.layer.cornerRadius = cornerRadius.value.toDouble()
            }
        )
        // Add a tint overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}
