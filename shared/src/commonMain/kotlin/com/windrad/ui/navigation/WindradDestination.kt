package com.windrad.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.windrad.ui.icons.CollectCardsBase
import com.windrad.ui.icons.Map as MapIcon
import com.windrad.ui.icons.MessageSquare

enum class WindradDestination(
    val label: String,
    val icon: ImageVector
) {
    MAP("Karte", MapIcon),
    COLLECTION("Sammlung", CollectCardsBase),
    FAQ("FAQ", MessageSquare)
}
