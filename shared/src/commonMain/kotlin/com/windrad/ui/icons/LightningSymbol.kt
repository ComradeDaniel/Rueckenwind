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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

val LightningSymbol: ImageVector
    get() {
        val current = _lightningSymbol
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.LightningSymbol",
            defaultWidth = 22.0.dp,
            defaultHeight = 22.0.dp,
            viewportWidth = 22.0f,
            viewportHeight = 22.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF0088FF)),
            ) {
                moveTo(x = 10.8076f, y = 21.6235f)
                curveTo(
                    x1 = 9.61784f,
                    y1 = 21.6235f,
                    x2 = 8.48063f,
                    y2 = 21.4409f,
                    x3 = 7.396f,
                    y3 = 21.0757f
                )
                curveTo(
                    x1 = 6.31689f,
                    y1 = 20.716f,
                    x2 = 5.32633f,
                    y2 = 20.2041f,
                    x3 = 4.42432f,
                    y3 = 19.54f
                )
                curveTo(
                    x1 = 3.52783f,
                    y1 = 18.876f,
                    x2 = 2.74756f,
                    y2 = 18.0929f,
                    x3 = 2.0835f,
                    y3 = 17.1909f
                )
                curveTo(
                    x1 = 1.41943f,
                    y1 = 16.2944f,
                    x2 = 0.904785f,
                    y2 = 15.3066f,
                    x3 = 0.539551f,
                    y3 = 14.2275f
                )
                curveTo(
                    x1 = 0.17985f,
                    y1 = 13.1429f,
                    x2 = 0.0f,
                    y2 = 12.0057f,
                    x3 = 0.0f,
                    y3 = 10.8159f
                )
                curveTo(
                    x1 = 0.0f,
                    y1 = 9.62061f,
                    x2 = 0.17985f,
                    y2 = 8.4834f,
                    x3 = 0.539551f,
                    y3 = 7.4043f
                )
                curveTo(
                    x1 = 0.904785f,
                    y1 = 6.3252f,
                    x2 = 1.41943f,
                    y2 = 5.33464f,
                    x3 = 2.0835f,
                    y3 = 4.43262f
                )
                curveTo(
                    x1 = 2.74756f,
                    y1 = 3.5306f,
                    x2 = 3.52783f,
                    y2 = 2.75033f,
                    x3 = 4.42432f,
                    y3 = 2.0918f
                )
                curveTo(
                    x1 = 5.32633f,
                    y1 = 1.42773f,
                    x2 = 6.31689f,
                    y2 = 0.913086f,
                    x3 = 7.396f,
                    y3 = 0.547852f
                )
                curveTo(
                    x1 = 8.48063f,
                    y1 = 0.182617f,
                    x2 = 9.61784f,
                    y2 = 0.0f,
                    x3 = 10.8076f,
                    y3 = 0.0f
                )
                curveTo(
                    x1 = 12.0029f,
                    y1 = 0.0f,
                    x2 = 13.1401f,
                    y2 = 0.182617f,
                    x3 = 14.2192f,
                    y3 = 0.547852f
                )
                curveTo(
                    x1 = 15.2983f,
                    y1 = 0.913086f,
                    x2 = 16.2889f,
                    y2 = 1.42773f,
                    x3 = 17.1909f,
                    y3 = 2.0918f
                )
                curveTo(
                    x1 = 18.0929f,
                    y1 = 2.75033f,
                    x2 = 18.8732f,
                    y2 = 3.5306f,
                    x3 = 19.5317f,
                    y3 = 4.43262f
                )
                curveTo(
                    x1 = 20.1958f,
                    y1 = 5.33464f,
                    x2 = 20.7104f,
                    y2 = 6.3252f,
                    x3 = 21.0757f,
                    y3 = 7.4043f
                )
                curveTo(
                    x1 = 21.4409f,
                    y1 = 8.4834f,
                    x2 = 21.6235f,
                    y2 = 9.62061f,
                    x3 = 21.6235f,
                    y3 = 10.8159f
                )
                curveTo(
                    x1 = 21.6235f,
                    y1 = 12.0057f,
                    x2 = 21.4409f,
                    y2 = 13.1429f,
                    x3 = 21.0757f,
                    y3 = 14.2275f
                )
                curveTo(
                    x1 = 20.7104f,
                    y1 = 15.3066f,
                    x2 = 20.1958f,
                    y2 = 16.2944f,
                    x3 = 19.5317f,
                    y3 = 17.1909f
                )
                curveTo(
                    x1 = 18.8732f,
                    y1 = 18.0929f,
                    x2 = 18.0929f,
                    y2 = 18.876f,
                    x3 = 17.1909f,
                    y3 = 19.54f
                )
                curveTo(
                    x1 = 16.2889f,
                    y1 = 20.2041f,
                    x2 = 15.2983f,
                    y2 = 20.716f,
                    x3 = 14.2192f,
                    y3 = 21.0757f
                )
                curveTo(
                    x1 = 13.1401f,
                    y1 = 21.4409f,
                    x2 = 12.0029f,
                    y2 = 21.6235f,
                    x3 = 10.8076f,
                    y3 = 21.6235f
                )
                close()
                moveTo(x = 10.8076f, y = 20.0796f)
                curveTo(
                    x1 = 11.8314f,
                    y1 = 20.0796f,
                    x2 = 12.8053f,
                    y2 = 19.9219f,
                    x3 = 13.7295f,
                    y3 = 19.6064f
                )
                curveTo(
                    x1 = 14.6592f,
                    y1 = 19.2965f,
                    x2 = 15.5086f,
                    y2 = 18.8566f,
                    x3 = 16.2778f,
                    y3 = 18.2866f
                )
                curveTo(
                    x1 = 17.0526f,
                    y1 = 17.7222f,
                    x2 = 17.7222f,
                    y2 = 17.0553f,
                    x3 = 18.2866f,
                    y3 = 16.2861f
                )
                curveTo(
                    x1 = 18.8566f,
                    y1 = 15.5114f,
                    x2 = 19.2965f,
                    y2 = 14.6619f,
                    x3 = 19.6064f,
                    y3 = 13.7378f
                )
                curveTo(
                    x1 = 19.9163f,
                    y1 = 12.8136f,
                    x2 = 20.0713f,
                    y2 = 11.8397f,
                    x3 = 20.0713f,
                    y3 = 10.8159f
                )
                curveTo(
                    x1 = 20.0713f,
                    y1 = 9.79215f,
                    x2 = 19.9163f,
                    y2 = 8.8182f,
                    x3 = 19.6064f,
                    y3 = 7.89404f
                )
                curveTo(
                    x1 = 19.2965f,
                    y1 = 6.96436f,
                    x2 = 18.8566f,
                    y2 = 6.11491f,
                    x3 = 18.2866f,
                    y3 = 5.3457f
                )
                curveTo(
                    x1 = 17.7222f,
                    y1 = 4.57096f,
                    x2 = 17.0526f,
                    y2 = 3.90137f,
                    x3 = 16.2778f,
                    y3 = 3.33691f
                )
                curveTo(
                    x1 = 15.5086f,
                    y1 = 2.76693f,
                    x2 = 14.6592f,
                    y2 = 2.32699f,
                    x3 = 13.7295f,
                    y3 = 2.01709f
                )
                curveTo(
                    x1 = 12.8053f,
                    y1 = 1.70719f,
                    x2 = 11.8314f,
                    y2 = 1.55225f,
                    x3 = 10.8076f,
                    y3 = 1.55225f
                )
                curveTo(
                    x1 = 9.78385f,
                    y1 = 1.55225f,
                    x2 = 8.8099f,
                    y2 = 1.70719f,
                    x3 = 7.88574f,
                    y3 = 2.01709f
                )
                curveTo(
                    x1 = 6.96159f,
                    y1 = 2.32699f,
                    x2 = 6.11214f,
                    y2 = 2.76693f,
                    x3 = 5.3374f,
                    y3 = 3.33691f
                )
                curveTo(
                    x1 = 4.5682f,
                    y1 = 3.90137f,
                    x2 = 3.8986f,
                    y2 = 4.57096f,
                    x3 = 3.32861f,
                    y3 = 5.3457f
                )
                curveTo(
                    x1 = 2.76416f,
                    y1 = 6.11491f,
                    x2 = 2.32422f,
                    y2 = 6.96436f,
                    x3 = 2.00879f,
                    y3 = 7.89404f
                )
                curveTo(
                    x1 = 1.69889f,
                    y1 = 8.8182f,
                    x2 = 1.54395f,
                    y2 = 9.79215f,
                    x3 = 1.54395f,
                    y3 = 10.8159f
                )
                curveTo(
                    x1 = 1.54395f,
                    y1 = 11.8397f,
                    x2 = 1.69889f,
                    y2 = 12.8136f,
                    x3 = 2.00879f,
                    y3 = 13.7378f
                )
                curveTo(
                    x1 = 2.32422f,
                    y1 = 14.6619f,
                    x2 = 2.76416f,
                    y2 = 15.5114f,
                    x3 = 3.32861f,
                    y3 = 16.2861f
                )
                curveTo(
                    x1 = 3.8986f,
                    y1 = 17.0553f,
                    x2 = 4.5682f,
                    y2 = 17.7222f,
                    x3 = 5.3374f,
                    y3 = 18.2866f
                )
                curveTo(
                    x1 = 6.11214f,
                    y1 = 18.8566f,
                    x2 = 6.96159f,
                    y2 = 19.2965f,
                    x3 = 7.88574f,
                    y3 = 19.6064f
                )
                curveTo(
                    x1 = 8.8099f,
                    y1 = 19.9219f,
                    x2 = 9.78385f,
                    y2 = 20.0796f,
                    x3 = 10.8076f,
                    y3 = 20.0796f
                )
                close()
                moveTo(x = 6.07617f, y = 11.5962f)
                curveTo(
                    x1 = 6.07617f,
                    y1 = 11.4689f,
                    x2 = 6.13428f,
                    y2 = 11.3361f,
                    x3 = 6.25049f,
                    y3 = 11.1978f
                )
                lineTo(x = 12.2188f, y = 3.69385f)
                curveTo(
                    x1 = 12.3571f,
                    y1 = 3.5223f,
                    x2 = 12.5093f,
                    y2 = 3.43929f,
                    x3 = 12.6753f,
                    y3 = 3.44482f
                )
                curveTo(
                    x1 = 12.8413f,
                    y1 = 3.45036f,
                    x2 = 12.9686f,
                    y2 = 3.51953f,
                    x3 = 13.0571f,
                    y3 = 3.65234f
                )
                curveTo(
                    x1 = 13.1457f,
                    y1 = 3.78516f,
                    x2 = 13.1484f,
                    y2 = 3.95671f,
                    x3 = 13.0654f,
                    y3 = 4.16699f
                )
                lineTo(x = 11.0898f, y = 9.49609f)
                horizontalLineTo(x = 14.7837f)
                curveTo(
                    x1 = 14.911f,
                    y1 = 9.49609f,
                    x2 = 15.0161f,
                    y2 = 9.53483f,
                    x3 = 15.0991f,
                    y3 = 9.6123f
                )
                curveTo(
                    x1 = 15.1877f,
                    y1 = 9.68978f,
                    x2 = 15.2319f,
                    y2 = 9.78939f,
                    x3 = 15.2319f,
                    y3 = 9.91113f
                )
                curveTo(
                    x1 = 15.2319f,
                    y1 = 10.0439f,
                    x2 = 15.1766f,
                    y2 = 10.1768f,
                    x3 = 15.0659f,
                    y3 = 10.3096f
                )
                lineTo(x = 9.08936f, y = 17.8135f)
                curveTo(
                    x1 = 8.95101f,
                    y1 = 17.985f,
                    x2 = 8.79883f,
                    y2 = 18.068f,
                    x3 = 8.63281f,
                    y3 = 18.0625f
                )
                curveTo(
                    x1 = 8.47233f,
                    y1 = 18.057f,
                    x2 = 8.34505f,
                    y2 = 17.9878f,
                    x3 = 8.25098f,
                    y3 = 17.855f
                )
                curveTo(
                    x1 = 8.16243f,
                    y1 = 17.7277f,
                    x2 = 8.15967f,
                    y2 = 17.5562f,
                    x3 = 8.24268f,
                    y3 = 17.3403f
                )
                lineTo(x = 10.2183f, y = 12.0112f)
                horizontalLineTo(x = 6.52441f)
                curveTo(
                    x1 = 6.40267f,
                    y1 = 12.0112f,
                    x2 = 6.29753f,
                    y2 = 11.9725f,
                    x3 = 6.20898f,
                    y3 = 11.895f
                )
                curveTo(
                    x1 = 6.12044f,
                    y1 = 11.8175f,
                    x2 = 6.07617f,
                    y2 = 11.7179f,
                    x3 = 6.07617f,
                    y3 = 11.5962f
                )
                close()
            }
        }.build().also { _lightningSymbol = it }
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
                imageVector = LightningSymbol,
                contentDescription = null,
                modifier = Modifier
                    .width((22.0).dp)
                    .height((22.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _lightningSymbol: ImageVector? = null
