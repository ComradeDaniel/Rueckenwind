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
    val Co2Green = Color(0xFF1FA734)     // CO2 KPI card accent
    val MetricOrange = Color(0xFFFF9500) // MWh bar: 50–500 range
    val MetricRed = Color(0xFFFF3B30)    // MWh bar: 0–50 range
    val MetricMint = Color(0xFF00C8B3)  // Battery indicator cells 3–4
    val KpiRed = Color(0xFFFF383C)       // Windpark-Auslastung pill
    val IndicatorBg = Color(0xFFF2F2F7)  // Icon/battery indicator background

    // UI Neutrals & Texts
    val DarkText = Color(0xFF1A1C1E)     // OnBackground / OnSurface / primary text
    val SubtitleText = Color(0xFF565E71) // Secondary text color / unselected icon
    val DarkIcon = Color(0xFF1E1E1E)     // Unselected tab icon / design dark grey
    val LightGray = Color(0xFFE5E5EA)    // iOS system light gray
    val DarkGray = Color(0xFF3C3C43)     // iOS label/text secondary
    val PureDark = Color(0xFF1A1A1A)     // Scan title / very dark
    val SecondaryLabel = Color(0x993C3C43) // iOS secondary label rgba(60,60,67,0.6)

    // Backgrounds & Containers
    val WhiteGlass = Color(0xF2FFFFFF)   // Bottom sheet / bottom bar background (95% White Glass)
    val SelectedItemOverlay =
        Color(0x14000000) // Semi-transparent black overlay (bottom bar selected item)
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
    val PlannedBadgeBg = Color(0xFFFFF9E6)
    val PlannedBadgeText = Color(0xFF8A6D00)
    val InactiveBadgeBg = Color(0xFFE5E5EA)
    val InactiveBadgeText = Color(0xFF555555)
}
