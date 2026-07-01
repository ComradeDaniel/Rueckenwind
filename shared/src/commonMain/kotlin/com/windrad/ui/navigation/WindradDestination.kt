package com.windrad.ui.navigation

import org.jetbrains.compose.resources.DrawableResource
import windrad.shared.generated.resources.*

enum class WindradDestination(
    val label: String,
    val icon: DrawableResource
) {
    MAP("Karte", Res.drawable.map),
    COLLECTION("Sammlung", Res.drawable.collect_cards_base),
    FAQ("FAQ", Res.drawable.message_square)
}
