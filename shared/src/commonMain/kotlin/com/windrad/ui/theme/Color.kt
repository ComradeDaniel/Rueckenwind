package com.windrad.ui.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    // Brand Colors & Figma Accents
    val Blue = Color(0xFF1D7AED)         // Design Primary Blue
    val AccentBlue = Color(0xFF007AFF)   // Secondary / Cluster Blue
    val MapsBlue = Color(0xFF0088FF)     // Maps Pin Blue
    val Green = Color(0xFF34C759)        // Accent Green (In Betrieb / collected)
    val Yellow = Color(0xFFFFCC00)       // Accent Yellow (In Planung)
    val Gray = Color(0xFFB3B3B3)         // Accent Gray (Außer Betrieb)
    
    // UI Neutrals & Texts
    val DarkText = Color(0xFF1A1C1E)     // OnBackground / OnSurface / primary text
    val SubtitleText = Color(0xFF565E71) // Secondary text color / unselected icon
    val LightGray = Color(0xFFE5E5EA)    // iOS system light gray
    val DarkGray = Color(0xFF3C3C43)     // iOS label/text secondary
    val PureDark = Color(0xFF1A1A1A)     // Scan title / very dark
    
    // Backgrounds & Containers
    val WhiteGlass = Color(0xF2FFFFFF)   // Bottom sheet / bottom bar background (95% White Glass)
    val SelectedItemOverlay = Color(0x14000000) // Semi-transparent black overlay (bottom bar selected item)
    val PrimaryContainer = Color(0xFFD6E4FF)
    val OnPrimaryContainer = Color(0xFF001945)
    val SecondaryContainer = Color(0xFFDAE2F9)
    val OnSecondaryContainer = Color(0xFF131C2C)
    val Tertiary = Color(0xFF705574)
    val TertiaryContainer = Color(0xFFFAD8FC)
    val OnTertiaryContainer = Color(0xFF29132D)
    val Background = Color(0xFFFCFCFF)
    val SurfaceVariant = Color(0xFFE1E2EC)
    val OnSurfaceVariant = Color(0xFF44474F)
    val Outline = Color(0xFF74777F)
    val InverseOnSurface = Color(0xFFF1F0F4)
    val InverseSurface = Color(0xFF2F3033)
    val InversePrimary = Color(0xFFA9C7FF)
    val OutlineVariant = Color(0xFFC4C6D0)
    val Scrim = Color(0xFF000000)

    // Custom Badge Colors
    val ActiveBadgeBg = Color(0xFFE2F0D9)
    val ActiveBadgeText = Color(0xFF385723)
}
