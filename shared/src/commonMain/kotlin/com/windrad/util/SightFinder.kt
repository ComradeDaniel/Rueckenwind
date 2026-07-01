package com.windrad.util

import org.jetbrains.compose.resources.DrawableResource
import windrad.shared.generated.resources.*

data class SightInfo(
    val name: String,
    val icon: DrawableResource,
    val height: Double
)

object SightFinder {
    val sights = listOf(
        SightInfo("Standard-Einkaufswagen", Res.drawable.einkaufswagen, 1.0),
        SightInfo("Fußballtor", Res.drawable.tor_icon, 2.5),
        SightInfo("Berliner Doppeldeckerbus", Res.drawable.doppeldecker, 4.0),
        SightInfo("Straßenlaterne", Res.drawable.lantern_icon, 6.0),
        SightInfo("Einfamilienhaus", Res.drawable.house_icon, 10.0),
        SightInfo("Hollywood-Sign", Res.drawable.hollywood_sign_icon, 14.0),
        SightInfo("Sphinx von Gizeh", Res.drawable.sphinx_von_gizeh, 20.0),
        SightInfo("Brandenburger Tor", Res.drawable.brandenburger_tor_icon, 26.0),
        SightInfo("Christusstatue", Res.drawable.jesus_statue_icon, 30.0),
        SightInfo("Leuchtturm Westerheversand", Res.drawable.light_house_icon, 41.0),
        SightInfo("Schiefer Turm von Pisa", Res.drawable.pisa_tower_icon, 55.0),
        SightInfo("Siegessäule", Res.drawable.victory_column_icon, 67.0),
        SightInfo("Freiheitsstatue", Res.drawable.statue_of_liberty_icon, 93.0),
        SightInfo("Münchner Frauenkirche", Res.drawable.church_icon, 100.0),
        SightInfo("Elbphilharmonie", Res.drawable.elbphilharmonie_icon, 110.0),
        SightInfo("Cheops-Pyramide", Res.drawable.pyramid_icon, 137.0),
        SightInfo("Kölner Dom", Res.drawable.koelner_dom_icon, 157.0),
        SightInfo("Main Tower", Res.drawable.main_tower_icon, 200.0),
        SightInfo("Olympiaturm", Res.drawable.olympia_tower_icon, 291.0),
        SightInfo("Eiffelturm", Res.drawable.eifel_tower_icon, 300.0),
        SightInfo("Berliner Fernsehturm", Res.drawable.berlin_fernseh_tower_icon, 368.0)
    )

    fun findNearestSight(targetHeight: Double): SightInfo {
        return sights.minBy { kotlin.math.abs(it.height - targetHeight) }
    }
}
