package com.windrad.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LinkIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Link",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).path(
        fill = SolidColor(Color.White),
        fillAlpha = 1.0f,
        strokeAlpha = 1.0f,
        strokeLineWidth = 0.0f,
        strokeLineCap = StrokeCap.Butt,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(3.9f, 12f)
        curveTo(3.9f, 10.29f, 5.29f, 8.9f, 7f, 8.9f)
        horizontalLineTo(11f)
        verticalLineTo(7f)
        horizontalLineTo(7f)
        curveTo(4.24f, 7f, 2f, 9.24f, 2f, 12f)
        curveTo(2f, 14.76f, 4.24f, 17f, 7f, 17f)
        horizontalLineTo(11f)
        verticalLineTo(15.1f)
        horizontalLineTo(7f)
        curveTo(5.29f, 15.1f, 3.9f, 13.71f, 3.9f, 12f)
        close()
        moveTo(8f, 13f)
        horizontalLineTo(16f)
        verticalLineTo(11f)
        horizontalLineTo(8f)
        verticalLineTo(13f)
        close()
        moveTo(17f, 8.9f)
        horizontalLineTo(13f)
        verticalLineTo(7f)
        horizontalLineTo(17f)
        curveTo(19.76f, 7f, 22f, 9.24f, 22f, 12f)
        curveTo(22f, 14.76f, 19.76f, 17f, 17f, 17f)
        horizontalLineTo(13f)
        verticalLineTo(15.1f)
        horizontalLineTo(17f)
        curveTo(18.71f, 15.1f, 20.1f, 13.71f, 20.1f, 12f)
        curveTo(20.1f, 10.29f, 18.71f, 8.9f, 17f, 8.9f)
        close()
    }.build()
