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

val MessageSquare: ImageVector
    get() {
        val current = _messageSquare
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.MessageSquare",
            defaultWidth = 20.5.dp,
            defaultHeight = 20.5.dp,
            viewportWidth = 20.5f,
            viewportHeight = 20.5f,
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineWidth = 2.5f,
            ) {
                moveTo(x = 19.25f, y = 13.25f)
                curveTo(
                    x1 = 19.25f,
                    y1 = 13.7804f,
                    x2 = 19.0393f,
                    y2 = 14.2891f,
                    x3 = 18.6642f,
                    y3 = 14.6642f
                )
                curveTo(
                    x1 = 18.2891f,
                    y1 = 15.0393f,
                    x2 = 17.7804f,
                    y2 = 15.25f,
                    x3 = 17.25f,
                    y3 = 15.25f
                )
                horizontalLineTo(x = 5.25f)
                lineTo(x = 1.25f, y = 19.25f)
                verticalLineTo(y = 3.25f)
                curveTo(
                    x1 = 1.25f,
                    y1 = 2.71957f,
                    x2 = 1.46071f,
                    y2 = 2.21086f,
                    x3 = 1.83579f,
                    y3 = 1.83579f
                )
                curveTo(
                    x1 = 2.21086f,
                    y1 = 1.46071f,
                    x2 = 2.71957f,
                    y2 = 1.25f,
                    x3 = 3.25f,
                    y3 = 1.25f
                )
                horizontalLineTo(x = 17.25f)
                curveTo(
                    x1 = 17.7804f,
                    y1 = 1.25f,
                    x2 = 18.2891f,
                    y2 = 1.46071f,
                    x3 = 18.6642f,
                    y3 = 1.83579f
                )
                curveTo(
                    x1 = 19.0393f,
                    y1 = 2.21086f,
                    x2 = 19.25f,
                    y2 = 2.71957f,
                    x3 = 19.25f,
                    y3 = 3.25f
                )
                verticalLineTo(y = 13.25f)
                close()
            }
        }.build().also { _messageSquare = it }
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
                imageVector = MessageSquare,
                contentDescription = null,
                modifier = Modifier
                    .width((20.5).dp)
                    .height((20.5).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _messageSquare: ImageVector? = null
