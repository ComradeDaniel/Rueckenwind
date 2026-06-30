package com.windrad.ui.collection.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.theme.AppColors

@Composable
fun CollectionTopBar(
    title: String,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(36.dp)
                .shadow(elevation = 4.dp, shape = CircleShape, clip = false)
                .background(Color.White, CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onBackClicked,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "‹",
                fontSize = 24.sp,
                color = AppColors.DarkGray,
                fontWeight = FontWeight.Normal,
            )
        }

        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )
    }
}
