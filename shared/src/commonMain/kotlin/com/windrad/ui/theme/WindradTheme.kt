package com.windrad.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = AppColors.Blue,
    onPrimary = Color.White,
    primaryContainer = AppColors.PrimaryContainer,
    onPrimaryContainer = AppColors.OnPrimaryContainer,
    secondary = AppColors.SubtitleText,
    onSecondary = Color.White,
    secondaryContainer = AppColors.SecondaryContainer,
    onSecondaryContainer = AppColors.OnSecondaryContainer,
    tertiary = AppColors.Tertiary,
    onTertiary = Color.White,
    tertiaryContainer = AppColors.TertiaryContainer,
    onTertiaryContainer = AppColors.OnTertiaryContainer,
    background = AppColors.Background,
    onBackground = AppColors.DarkText,
    surface = AppColors.Background,
    onSurface = AppColors.DarkText,
    surfaceVariant = AppColors.SurfaceVariant,
    onSurfaceVariant = AppColors.OnSurfaceVariant,
    outline = AppColors.Outline,
    inverseOnSurface = AppColors.InverseOnSurface,
    inverseSurface = AppColors.InverseSurface,
    inversePrimary = AppColors.InversePrimary,
    surfaceTint = AppColors.Blue,
    outlineVariant = AppColors.OutlineVariant,
    scrim = AppColors.Scrim
)

@Composable
fun WindradTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
