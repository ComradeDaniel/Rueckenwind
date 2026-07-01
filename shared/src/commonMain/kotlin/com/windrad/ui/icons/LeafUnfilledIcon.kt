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

val LeafUnfilledIcon: ImageVector
    get() {
        val current = _leafUnfilledIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.LeafUnfilledIcon",
            defaultWidth = 22.0.dp,
            defaultHeight = 22.0.dp,
            viewportWidth = 22.0f,
            viewportHeight = 22.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
            ) {
                moveTo(x = 1.57324f, y = 6.28613f)
                curveTo(
                    x1 = 1.57324f,
                    y1 = 6.12793f,
                    x2 = 1.5791f,
                    y2 = 5.87891f,
                    x3 = 1.59082f,
                    y3 = 5.53906f
                )
                curveTo(
                    x1 = 1.6084f,
                    y1 = 5.19922f,
                    x2 = 1.63477f,
                    y2 = 4.83887f,
                    x3 = 1.66992f,
                    y3 = 4.45801f
                )
                curveTo(
                    x1 = 1.71094f,
                    y1 = 4.07715f,
                    x2 = 1.7666f,
                    y2 = 3.74316f,
                    x3 = 1.83691f,
                    y3 = 3.45605f
                )
                curveTo(
                    x1 = 1.91895f,
                    y1 = 3.12207f,
                    x2 = 2.06836f,
                    y2 = 2.89648f,
                    x3 = 2.28516f,
                    y3 = 2.7793f
                )
                curveTo(
                    x1 = 2.50195f,
                    y1 = 2.66211f,
                    x2 = 2.7334f,
                    y2 = 2.63574f,
                    x3 = 2.97949f,
                    y3 = 2.7002f
                )
                curveTo(
                    x1 = 3.22559f,
                    y1 = 2.76465f,
                    x2 = 3.43359f,
                    y2 = 2.89648f,
                    x3 = 3.60352f,
                    y3 = 3.0957f
                )
                curveTo(
                    x1 = 4.08984f,
                    y1 = 3.64648f,
                    x2 = 4.62305f,
                    y2 = 4.03613f,
                    x3 = 5.20312f,
                    y3 = 4.26465f
                )
                curveTo(
                    x1 = 5.7832f,
                    y1 = 4.49316f,
                    x2 = 6.41309f,
                    y2 = 4.60742f,
                    x3 = 7.09277f,
                    y3 = 4.60742f
                )
                curveTo(
                    x1 = 7.89551f,
                    y1 = 4.60742f,
                    x2 = 8.66309f,
                    y2 = 4.54004f,
                    x3 = 9.39551f,
                    y3 = 4.40527f
                )
                curveTo(
                    x1 = 10.1338f,
                    y1 = 4.27051f,
                    x2 = 10.8633f,
                    y2 = 4.20312f,
                    x3 = 11.584f,
                    y3 = 4.20312f
                )
                curveTo(
                    x1 = 12.6211f,
                    y1 = 4.20312f,
                    x2 = 13.5762f,
                    y2 = 4.36133f,
                    x3 = 14.4492f,
                    y3 = 4.67773f
                )
                curveTo(
                    x1 = 15.3281f,
                    y1 = 4.98828f,
                    x2 = 16.0869f,
                    y2 = 5.43066f,
                    x3 = 16.7256f,
                    y3 = 6.00488f
                )
                curveTo(
                    x1 = 17.3701f,
                    y1 = 6.57324f,
                    x2 = 17.8682f,
                    y2 = 7.24707f,
                    x3 = 18.2197f,
                    y3 = 8.02637f
                )
                curveTo(
                    x1 = 18.5771f,
                    y1 = 8.7998f,
                    x2 = 18.7559f,
                    y2 = 9.64941f,
                    x3 = 18.7559f,
                    y3 = 10.5752f
                )
                curveTo(
                    x1 = 18.7559f,
                    y1 = 11.5361f,
                    x2 = 18.5771f,
                    y2 = 12.418f,
                    x3 = 18.2197f,
                    y3 = 13.2207f
                )
                curveTo(
                    x1 = 17.8682f,
                    y1 = 14.0234f,
                    x2 = 17.3701f,
                    y2 = 14.7178f,
                    x3 = 16.7256f,
                    y3 = 15.3037f
                )
                curveTo(
                    x1 = 16.0869f,
                    y1 = 15.8896f,
                    x2 = 15.3281f,
                    y2 = 16.3438f,
                    x3 = 14.4492f,
                    y3 = 16.666f
                )
                curveTo(
                    x1 = 13.5703f,
                    y1 = 16.9883f,
                    x2 = 12.6035f,
                    y2 = 17.1494f,
                    x3 = 11.5488f,
                    y3 = 17.1494f
                )
                curveTo(
                    x1 = 10.0371f,
                    y1 = 17.1494f,
                    x2 = 8.66895f,
                    y2 = 16.8945f,
                    x3 = 7.44434f,
                    y3 = 16.3848f
                )
                curveTo(
                    x1 = 6.21973f,
                    y1 = 15.8691f,
                    x2 = 5.16797f,
                    y2 = 15.1338f,
                    x3 = 4.28906f,
                    y3 = 14.1787f
                )
                curveTo(
                    x1 = 3.41602f,
                    y1 = 13.2236f,
                    x2 = 2.74512f,
                    y2 = 12.0811f,
                    x3 = 2.27637f,
                    y3 = 10.751f
                )
                curveTo(
                    x1 = 1.80762f,
                    y1 = 9.41504f,
                    x2 = 1.57324f,
                    y2 = 7.92676f,
                    x3 = 1.57324f,
                    y3 = 6.28613f
                )
                close()
                moveTo(x = 2.90039f, y = 6.28613f)
                curveTo(
                    x1 = 2.90039f,
                    y1 = 7.74512f,
                    x2 = 3.09961f,
                    y2 = 9.06055f,
                    x3 = 3.49805f,
                    y3 = 10.2324f
                )
                curveTo(
                    x1 = 3.90234f,
                    y1 = 11.4043f,
                    x2 = 4.48242f,
                    y2 = 12.4062f,
                    x3 = 5.23828f,
                    y3 = 13.2383f
                )
                curveTo(
                    x1 = 5.99414f,
                    y1 = 14.0703f,
                    x2 = 6.90234f,
                    y2 = 14.709f,
                    x3 = 7.96289f,
                    y3 = 15.1543f
                )
                curveTo(
                    x1 = 9.0293f,
                    y1 = 15.5996f,
                    x2 = 10.2246f,
                    y2 = 15.8223f,
                    x3 = 11.5488f,
                    y3 = 15.8223f
                )
                curveTo(
                    x1 = 12.709f,
                    y1 = 15.8223f,
                    x2 = 13.7285f,
                    y2 = 15.5996f,
                    x3 = 14.6074f,
                    y3 = 15.1543f
                )
                curveTo(
                    x1 = 15.4922f,
                    y1 = 14.709f,
                    x2 = 16.1836f,
                    y2 = 14.0938f,
                    x3 = 16.6816f,
                    y3 = 13.3086f
                )
                curveTo(
                    x1 = 17.1797f,
                    y1 = 12.5176f,
                    x2 = 17.4287f,
                    y2 = 11.6064f,
                    x3 = 17.4287f,
                    y3 = 10.5752f
                )
                curveTo(
                    x1 = 17.4287f,
                    y1 = 9.59082f,
                    x2 = 17.1797f,
                    y2 = 8.7207f,
                    x3 = 16.6816f,
                    y3 = 7.96484f
                )
                curveTo(
                    x1 = 16.1836f,
                    y1 = 7.20312f,
                    x2 = 15.4951f,
                    y2 = 6.6084f,
                    x3 = 14.6162f,
                    y3 = 6.18066f
                )
                curveTo(
                    x1 = 13.7373f,
                    y1 = 5.74707f,
                    x2 = 12.7266f,
                    y2 = 5.53027f,
                    x3 = 11.584f,
                    y3 = 5.53027f
                )
                curveTo(
                    x1 = 11.2852f,
                    y1 = 5.53027f,
                    x2 = 10.9805f,
                    y2 = 5.54785f,
                    x3 = 10.6699f,
                    y3 = 5.58301f
                )
                curveTo(
                    x1 = 10.3594f,
                    y1 = 5.61816f,
                    x2 = 10.0283f,
                    y2 = 5.65918f,
                    x3 = 9.67676f,
                    y3 = 5.70605f
                )
                curveTo(
                    x1 = 9.29004f,
                    y1 = 5.75879f,
                    x2 = 8.87988f,
                    y2 = 5.81152f,
                    x3 = 8.44629f,
                    y3 = 5.86426f
                )
                curveTo(
                    x1 = 8.01855f,
                    y1 = 5.91113f,
                    x2 = 7.56738f,
                    y2 = 5.93457f,
                    x3 = 7.09277f,
                    y3 = 5.93457f
                )
                curveTo(
                    x1 = 6.38379f,
                    y1 = 5.93457f,
                    x2 = 5.67188f,
                    y2 = 5.81445f,
                    x3 = 4.95703f,
                    y3 = 5.57422f
                )
                curveTo(
                    x1 = 4.24219f,
                    y1 = 5.32812f,
                    x2 = 3.63867f,
                    y2 = 4.97949f,
                    x3 = 3.14648f,
                    y3 = 4.52832f
                )
                curveTo(
                    x1 = 3.05859f,
                    y1 = 4.44629f,
                    x2 = 3.00586f,
                    y2 = 4.46387f,
                    x3 = 2.98828f,
                    y3 = 4.58105f
                )
                curveTo(
                    x1 = 2.94727f,
                    y1 = 4.81543f,
                    x2 = 2.9209f,
                    y2 = 5.08496f,
                    x3 = 2.90918f,
                    y3 = 5.38965f
                )
                curveTo(
                    x1 = 2.90332f,
                    y1 = 5.69434f,
                    x2 = 2.90039f,
                    y2 = 5.99316f,
                    x3 = 2.90039f,
                    y3 = 6.28613f
                )
                close()
                moveTo(x = 6.71484f, y = 8.33398f)
                curveTo(
                    x1 = 6.66797f,
                    y1 = 8.19922f,
                    x2 = 6.67676f,
                    y2 = 8.08789f,
                    x3 = 6.74121f,
                    y3 = 8.0f
                )
                curveTo(
                    x1 = 6.80566f,
                    y1 = 7.91211f,
                    x2 = 6.89062f,
                    y2 = 7.86816f,
                    x3 = 6.99609f,
                    y3 = 7.86816f
                )
                curveTo(
                    x1 = 7.10156f,
                    y1 = 7.8623f,
                    x2 = 7.20117f,
                    y2 = 7.92383f,
                    x3 = 7.29492f,
                    y3 = 8.05273f
                )
                curveTo(
                    x1 = 7.63477f,
                    y1 = 8.49219f,
                    x2 = 8.01855f,
                    y2 = 8.84375f,
                    x3 = 8.44629f,
                    y3 = 9.10742f
                )
                curveTo(
                    x1 = 8.87988f,
                    y1 = 9.37109f,
                    x2 = 9.34863f,
                    y2 = 9.5791f,
                    x3 = 9.85254f,
                    y3 = 9.73145f
                )
                curveTo(
                    x1 = 10.3623f,
                    y1 = 9.87793f,
                    x2 = 10.8955f,
                    y2 = 9.99805f,
                    x3 = 11.4521f,
                    y3 = 10.0918f
                )
                curveTo(
                    x1 = 13.0693f,
                    y1 = 10.3555f,
                    x2 = 14.3994f,
                    y2 = 10.7246f,
                    x3 = 15.4424f,
                    y3 = 11.1992f
                )
                curveTo(
                    x1 = 16.4854f,
                    y1 = 11.6738f,
                    x2 = 17.3027f,
                    y2 = 12.3037f,
                    x3 = 17.8945f,
                    y3 = 13.0889f
                )
                curveTo(
                    x1 = 18.123f,
                    y1 = 13.3994f,
                    x2 = 18.3691f,
                    y2 = 13.7568f,
                    x3 = 18.6328f,
                    y3 = 14.1611f
                )
                curveTo(
                    x1 = 18.9023f,
                    y1 = 14.5596f,
                    x2 = 19.1543f,
                    y2 = 14.9697f,
                    x3 = 19.3887f,
                    y3 = 15.3916f
                )
                curveTo(
                    x1 = 19.6289f,
                    y1 = 15.8135f,
                    x2 = 19.8223f,
                    y2 = 16.2207f,
                    x3 = 19.9688f,
                    y3 = 16.6133f
                )
                curveTo(
                    x1 = 20.1211f,
                    y1 = 17.0059f,
                    x2 = 20.1973f,
                    y2 = 17.3545f,
                    x3 = 20.1973f,
                    y3 = 17.6592f
                )
                curveTo(
                    x1 = 20.1973f,
                    y1 = 18.0225f,
                    x2 = 20.083f,
                    y2 = 18.3359f,
                    x3 = 19.8545f,
                    y3 = 18.5996f
                )
                curveTo(
                    x1 = 19.626f,
                    y1 = 18.8633f,
                    x2 = 19.3506f,
                    y2 = 18.9951f,
                    x3 = 19.0283f,
                    y3 = 18.9951f
                )
                curveTo(
                    x1 = 18.876f,
                    y1 = 18.9951f,
                    x2 = 18.7324f,
                    y2 = 18.9512f,
                    x3 = 18.5977f,
                    y3 = 18.8633f
                )
                curveTo(
                    x1 = 18.4688f,
                    y1 = 18.7812f,
                    x2 = 18.3691f,
                    y2 = 18.6348f,
                    x3 = 18.2988f,
                    y3 = 18.4238f
                )
                curveTo(
                    x1 = 18.1172f,
                    y1 = 17.8438f,
                    x2 = 17.9561f,
                    y2 = 17.2959f,
                    x3 = 17.8154f,
                    y3 = 16.7803f
                )
                curveTo(
                    x1 = 17.6748f,
                    y1 = 16.2646f,
                    x2 = 17.5078f,
                    y2 = 15.7783f,
                    x3 = 17.3145f,
                    y3 = 15.3213f
                )
                curveTo(
                    x1 = 17.127f,
                    y1 = 14.8643f,
                    x2 = 16.8691f,
                    y2 = 14.4336f,
                    x3 = 16.541f,
                    y3 = 14.0293f
                )
                curveTo(
                    x1 = 16.2422f,
                    y1 = 13.6543f,
                    x2 = 15.9229f,
                    y2 = 13.332f,
                    x3 = 15.583f,
                    y3 = 13.0625f
                )
                curveTo(
                    x1 = 15.249f,
                    y1 = 12.7871f,
                    x2 = 14.8682f,
                    y2 = 12.5469f,
                    x3 = 14.4404f,
                    y3 = 12.3418f
                )
                curveTo(
                    x1 = 14.0186f,
                    y1 = 12.1367f,
                    x2 = 13.5264f,
                    y2 = 11.9463f,
                    x3 = 12.9639f,
                    y3 = 11.7705f
                )
                curveTo(
                    x1 = 12.4014f,
                    y1 = 11.5947f,
                    x2 = 11.748f,
                    y2 = 11.4189f,
                    x3 = 11.0039f,
                    y3 = 11.2432f
                )
                curveTo(
                    x1 = 10.3125f,
                    y1 = 11.0732f,
                    x2 = 9.67676f,
                    y2 = 10.8711f,
                    x3 = 9.09668f,
                    y3 = 10.6367f
                )
                curveTo(
                    x1 = 8.5166f,
                    y1 = 10.4023f,
                    x2 = 8.01855f,
                    y2 = 10.1035f,
                    x3 = 7.60254f,
                    y3 = 9.74023f
                )
                curveTo(
                    x1 = 7.19238f,
                    y1 = 9.37109f,
                    x2 = 6.89648f,
                    y2 = 8.90234f,
                    x3 = 6.71484f,
                    y3 = 8.33398f
                )
                close()
            }
        }.build().also { _leafUnfilledIcon = it }
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
                imageVector = LeafUnfilledIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((22.0).dp)
                    .height((22.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _leafUnfilledIcon: ImageVector? = null
