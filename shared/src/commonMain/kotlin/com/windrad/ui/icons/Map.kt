package com.windrad.ui.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

val Map: ImageVector
    get() {
        val current = _map
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.Map",
            defaultWidth = 24.5.dp,
            defaultHeight = 22.5.dp,
            viewportWidth = 24.5f,
            viewportHeight = 22.5f,
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineWidth = 2.5f,
            ) {
                moveTo(x = 16.25f, y = 5.25f)
                lineTo(x = 8.25f, y = 1.25f)
                lineTo(x = 1.25f, y = 5.25f)
                verticalLineTo(y = 21.25f)
                lineTo(x = 8.25f, y = 17.25f)
                moveTo(x = 16.25f, y = 5.25f)
                lineTo(x = 23.25f, y = 1.25f)
                verticalLineTo(y = 17.25f)
                lineTo(x = 16.25f, y = 21.25f)
                lineTo(x = 8.25f, y = 17.25f)
                moveTo(x = 8.25f, y = 17.25f)
                verticalLineTo(y = 1.25f)
                moveTo(x = 16.25f, y = 21.25f)
                verticalLineTo(y = 5.25f)
            }
        }.build().also { _map = it }
    }

@Preview
@Composable
private fun IconPreview() {
    com.windrad.ui.theme.WindradTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = Map,
                contentDescription = null,
                modifier = Modifier
                    .width((24.5).dp)
                    .height((22.5).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _map: ImageVector? = null
