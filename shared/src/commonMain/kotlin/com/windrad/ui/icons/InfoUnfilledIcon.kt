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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val InfoUnfilledIcon: ImageVector
    get() {
        val current = _infoUnfilledIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.InfoUnfilledIcon",
            defaultWidth = 26.0.dp,
            defaultHeight = 23.0.dp,
            viewportWidth = 26.0f,
            viewportHeight = 23.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF757575)),
            ) {
                moveTo(x = 13.0f, y = 22.2012f)
                curveTo(
                    x1 = 11.4889f,
                    y1 = 22.2012f,
                    x2 = 10.071f,
                    y2 = 21.9147f,
                    x3 = 8.74609f,
                    y3 = 21.3418f
                )
                curveTo(
                    x1 = 7.42122f,
                    y1 = 20.776f,
                    x2 = 6.25749f,
                    y2 = 19.9919f,
                    x3 = 5.25488f,
                    y3 = 18.9893f
                )
                curveTo(
                    x1 = 4.25228f,
                    y1 = 17.9867f,
                    x2 = 3.46452f,
                    y2 = 16.8229f,
                    x3 = 2.8916f,
                    y3 = 15.498f
                )
                curveTo(
                    x1 = 2.32585f,
                    y1 = 14.1732f,
                    x2 = 2.04297f,
                    y2 = 12.7552f,
                    x3 = 2.04297f,
                    y3 = 11.2441f
                )
                curveTo(
                    x1 = 2.04297f,
                    y1 = 9.73307f,
                    x2 = 2.32585f,
                    y2 = 8.3151f,
                    x3 = 2.8916f,
                    y3 = 6.99023f
                )
                curveTo(
                    x1 = 3.46452f,
                    y1 = 5.66536f,
                    x2 = 4.25228f,
                    y2 = 4.50163f,
                    x3 = 5.25488f,
                    y3 = 3.49902f
                )
                curveTo(
                    x1 = 6.25749f,
                    y1 = 2.48926f,
                    x2 = 7.42122f,
                    y2 = 1.7015f,
                    x3 = 8.74609f,
                    y3 = 1.13574f
                )
                curveTo(
                    x1 = 10.071f,
                    y1 = 0.569987f,
                    x2 = 11.4889f,
                    y2 = 0.287109f,
                    x3 = 13.0f,
                    y3 = 0.287109f
                )
                curveTo(
                    x1 = 14.5111f,
                    y1 = 0.287109f,
                    x2 = 15.929f,
                    y2 = 0.569987f,
                    x3 = 17.2539f,
                    y3 = 1.13574f
                )
                curveTo(
                    x1 = 18.5788f,
                    y1 = 1.7015f,
                    x2 = 19.7425f,
                    y2 = 2.48926f,
                    x3 = 20.7451f,
                    y3 = 3.49902f
                )
                curveTo(
                    x1 = 21.7477f,
                    y1 = 4.50163f,
                    x2 = 22.5319f,
                    y2 = 5.66536f,
                    x3 = 23.0977f,
                    y3 = 6.99023f
                )
                curveTo(
                    x1 = 23.6706f,
                    y1 = 8.3151f,
                    x2 = 23.957f,
                    y2 = 9.73307f,
                    x3 = 23.957f,
                    y3 = 11.2441f
                )
                curveTo(
                    x1 = 23.957f,
                    y1 = 12.7552f,
                    x2 = 23.6706f,
                    y2 = 14.1732f,
                    x3 = 23.0977f,
                    y3 = 15.498f
                )
                curveTo(
                    x1 = 22.5319f,
                    y1 = 16.8229f,
                    x2 = 21.7477f,
                    y2 = 17.9867f,
                    x3 = 20.7451f,
                    y3 = 18.9893f
                )
                curveTo(
                    x1 = 19.7425f,
                    y1 = 19.9919f,
                    x2 = 18.5788f,
                    y2 = 20.776f,
                    x3 = 17.2539f,
                    y3 = 21.3418f
                )
                curveTo(
                    x1 = 15.929f,
                    y1 = 21.9147f,
                    x2 = 14.5111f,
                    y2 = 22.2012f,
                    x3 = 13.0f,
                    y3 = 22.2012f
                )
                close()
                moveTo(x = 13.0f, y = 20.375f)
                curveTo(
                    x1 = 14.2604f,
                    y1 = 20.375f,
                    x2 = 15.4421f,
                    y2 = 20.1387f,
                    x3 = 16.5449f,
                    y3 = 19.666f
                )
                curveTo(
                    x1 = 17.6478f,
                    y1 = 19.1934f,
                    x2 = 18.6182f,
                    y2 = 18.5381f,
                    x3 = 19.4561f,
                    y3 = 17.7002f
                )
                curveTo(
                    x1 = 20.2939f,
                    y1 = 16.8623f,
                    x2 = 20.9492f,
                    y2 = 15.8919f,
                    x3 = 21.4219f,
                    y3 = 14.7891f
                )
                curveTo(
                    x1 = 21.8945f,
                    y1 = 13.6862f,
                    x2 = 22.1309f,
                    y2 = 12.5046f,
                    x3 = 22.1309f,
                    y3 = 11.2441f
                )
                curveTo(
                    x1 = 22.1309f,
                    y1 = 9.98372f,
                    x2 = 21.8945f,
                    y2 = 8.80208f,
                    x3 = 21.4219f,
                    y3 = 7.69922f
                )
                curveTo(
                    x1 = 20.9492f,
                    y1 = 6.58919f,
                    x2 = 20.2939f,
                    y2 = 5.61882f,
                    x3 = 19.4561f,
                    y3 = 4.78809f
                )
                curveTo(
                    x1 = 18.6182f,
                    y1 = 3.9502f,
                    x2 = 17.6478f,
                    y2 = 3.29492f,
                    x3 = 16.5449f,
                    y3 = 2.82227f
                )
                curveTo(
                    x1 = 15.4421f,
                    y1 = 2.34961f,
                    x2 = 14.2604f,
                    y2 = 2.11328f,
                    x3 = 13.0f,
                    y3 = 2.11328f
                )
                curveTo(
                    x1 = 11.7396f,
                    y1 = 2.11328f,
                    x2 = 10.5579f,
                    y2 = 2.34961f,
                    x3 = 9.45508f,
                    y3 = 2.82227f
                )
                curveTo(
                    x1 = 8.35221f,
                    y1 = 3.29492f,
                    x2 = 7.38184f,
                    y2 = 3.9502f,
                    x3 = 6.54395f,
                    y3 = 4.78809f
                )
                curveTo(
                    x1 = 5.70605f,
                    y1 = 5.61882f,
                    x2 = 5.05078f,
                    y2 = 6.58919f,
                    x3 = 4.57812f,
                    y3 = 7.69922f
                )
                curveTo(
                    x1 = 4.10547f,
                    y1 = 8.80208f,
                    x2 = 3.86914f,
                    y2 = 9.98372f,
                    x3 = 3.86914f,
                    y3 = 11.2441f
                )
                curveTo(
                    x1 = 3.86914f,
                    y1 = 12.5046f,
                    x2 = 4.10547f,
                    y2 = 13.6862f,
                    x3 = 4.57812f,
                    y3 = 14.7891f
                )
                curveTo(
                    x1 = 5.05078f,
                    y1 = 15.8919f,
                    x2 = 5.70605f,
                    y2 = 16.8623f,
                    x3 = 6.54395f,
                    y3 = 17.7002f
                )
                curveTo(
                    x1 = 7.38184f,
                    y1 = 18.5381f,
                    x2 = 8.35221f,
                    y2 = 19.1934f,
                    x3 = 9.45508f,
                    y3 = 19.666f
                )
                curveTo(
                    x1 = 10.5579f,
                    y1 = 20.1387f,
                    x2 = 11.7396f,
                    y2 = 20.375f,
                    x3 = 13.0f,
                    y3 = 20.375f
                )
                close()
                moveTo(x = 11.1201f, y = 17.249f)
                curveTo(
                    x1 = 10.8981f,
                    y1 = 17.249f,
                    x2 = 10.7119f,
                    y2 = 17.1774f,
                    x3 = 10.5615f,
                    y3 = 17.0342f
                )
                curveTo(
                    x1 = 10.4111f,
                    y1 = 16.891f,
                    x2 = 10.3359f,
                    y2 = 16.7119f,
                    x3 = 10.3359f,
                    y3 = 16.4971f
                )
                curveTo(
                    x1 = 10.3359f,
                    y1 = 16.2822f,
                    x2 = 10.4111f,
                    y2 = 16.1032f,
                    x3 = 10.5615f,
                    y3 = 15.96f
                )
                curveTo(
                    x1 = 10.7119f,
                    y1 = 15.8167f,
                    x2 = 10.8981f,
                    y2 = 15.7451f,
                    x3 = 11.1201f,
                    y3 = 15.7451f
                )
                horizontalLineTo(x = 12.4521f)
                verticalLineTo(y = 10.8145f)
                horizontalLineTo(x = 11.3027f)
                curveTo(
                    x1 = 11.0807f,
                    y1 = 10.8145f,
                    x2 = 10.8945f,
                    y2 = 10.7428f,
                    x3 = 10.7441f,
                    y3 = 10.5996f
                )
                curveTo(
                    x1 = 10.5938f,
                    y1 = 10.4564f,
                    x2 = 10.5186f,
                    y2 = 10.2773f,
                    x3 = 10.5186f,
                    y3 = 10.0625f
                )
                curveTo(
                    x1 = 10.5186f,
                    y1 = 9.84766f,
                    x2 = 10.5938f,
                    y2 = 9.66862f,
                    x3 = 10.7441f,
                    y3 = 9.52539f
                )
                curveTo(
                    x1 = 10.8945f,
                    y1 = 9.38216f,
                    x2 = 11.0807f,
                    y2 = 9.31055f,
                    x3 = 11.3027f,
                    y3 = 9.31055f
                )
                horizontalLineTo(x = 13.3223f)
                curveTo(
                    x1 = 13.5944f,
                    y1 = 9.31055f,
                    x2 = 13.8021f,
                    y2 = 9.40007f,
                    x3 = 13.9453f,
                    y3 = 9.5791f
                )
                curveTo(
                    x1 = 14.0885f,
                    y1 = 9.75098f,
                    x2 = 14.1602f,
                    y2 = 9.98372f,
                    x3 = 14.1602f,
                    y3 = 10.2773f
                )
                verticalLineTo(y = 15.7451f)
                horizontalLineTo(x = 15.4922f)
                curveTo(
                    x1 = 15.7142f,
                    y1 = 15.7451f,
                    x2 = 15.9004f,
                    y2 = 15.8167f,
                    x3 = 16.0508f,
                    y3 = 15.96f
                )
                curveTo(
                    x1 = 16.2012f,
                    y1 = 16.1032f,
                    x2 = 16.2764f,
                    y2 = 16.2822f,
                    x3 = 16.2764f,
                    y3 = 16.4971f
                )
                curveTo(
                    x1 = 16.2764f,
                    y1 = 16.7119f,
                    x2 = 16.2012f,
                    y2 = 16.891f,
                    x3 = 16.0508f,
                    y3 = 17.0342f
                )
                curveTo(
                    x1 = 15.9004f,
                    y1 = 17.1774f,
                    x2 = 15.7142f,
                    y2 = 17.249f,
                    x3 = 15.4922f,
                    y3 = 17.249f
                )
                horizontalLineTo(x = 11.1201f)
                close()
                moveTo(x = 12.9033f, y = 7.5166f)
                curveTo(
                    x1 = 12.5166f,
                    y1 = 7.5166f,
                    x2 = 12.1872f,
                    y2 = 7.38053f,
                    x3 = 11.915f,
                    y3 = 7.1084f
                )
                curveTo(
                    x1 = 11.6429f,
                    y1 = 6.83626f,
                    x2 = 11.5068f,
                    y2 = 6.50684f,
                    x3 = 11.5068f,
                    y3 = 6.12012f
                )
                curveTo(
                    x1 = 11.5068f,
                    y1 = 5.72624f,
                    x2 = 11.6429f,
                    y2 = 5.39323f,
                    x3 = 11.915f,
                    y3 = 5.12109f
                )
                curveTo(
                    x1 = 12.1872f,
                    y1 = 4.84896f,
                    x2 = 12.5166f,
                    y2 = 4.71289f,
                    x3 = 12.9033f,
                    y3 = 4.71289f
                )
                curveTo(
                    x1 = 13.2972f,
                    y1 = 4.71289f,
                    x2 = 13.6266f,
                    y2 = 4.84896f,
                    x3 = 13.8916f,
                    y3 = 5.12109f
                )
                curveTo(
                    x1 = 14.1637f,
                    y1 = 5.39323f,
                    x2 = 14.2998f,
                    y2 = 5.72624f,
                    x3 = 14.2998f,
                    y3 = 6.12012f
                )
                curveTo(
                    x1 = 14.2998f,
                    y1 = 6.50684f,
                    x2 = 14.1637f,
                    y2 = 6.83626f,
                    x3 = 13.8916f,
                    y3 = 7.1084f
                )
                curveTo(
                    x1 = 13.6266f,
                    y1 = 7.38053f,
                    x2 = 13.2972f,
                    y2 = 7.5166f,
                    x3 = 12.9033f,
                    y3 = 7.5166f
                )
                close()
            }
        }.build().also { _infoUnfilledIcon = it }
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
                imageVector = InfoUnfilledIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((26.0).dp)
                    .height((23.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _infoUnfilledIcon: ImageVector? = null
