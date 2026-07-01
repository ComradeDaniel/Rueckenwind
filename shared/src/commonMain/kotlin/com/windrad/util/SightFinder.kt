package com.windrad.util

import androidx.compose.ui.graphics.vector.ImageVector
import com.windrad.ui.icons.BerlinFernsehTowerIcon
import com.windrad.ui.icons.BrandenburgerTorIcon
import com.windrad.ui.icons.ChurchIcon
import com.windrad.ui.icons.Doppeldecker
import com.windrad.ui.icons.EifelTowerIcon
import com.windrad.ui.icons.Einkaufswagen
import com.windrad.ui.icons.ElbphilharmonieIcon
import com.windrad.ui.icons.HollywoodSignIcon
import com.windrad.ui.icons.HouseIcon
import com.windrad.ui.icons.JesusStatueIcon
import com.windrad.ui.icons.KoelnerDomIcon
import com.windrad.ui.icons.LanternIcon
import com.windrad.ui.icons.LightHouseIcon
import com.windrad.ui.icons.MainTowerIcon
import com.windrad.ui.icons.OlympiaTowerIcon
import com.windrad.ui.icons.PisaTowerIcon
import com.windrad.ui.icons.PyramidIcon
import com.windrad.ui.icons.SphinxVonGizeh
import com.windrad.ui.icons.StatueOfLibertyIcon
import com.windrad.ui.icons.TorIcon
import com.windrad.ui.icons.VictoryColumnIcon

data class SightInfo(
    val name: String,
    val icon: ImageVector,
    val height: Double
)

object SightFinder {
    val sights = listOf(
        SightInfo("Standard-Einkaufswagen", Einkaufswagen, 1.0),
        SightInfo("Fußballtor", TorIcon, 2.5),
        SightInfo("Berliner Doppeldeckerbus", Doppeldecker, 4.0),
        SightInfo("Straßenlaterne", LanternIcon, 6.0),
        SightInfo("Einfamilienhaus", HouseIcon, 10.0),
        SightInfo("Hollywood-Sign", HollywoodSignIcon, 14.0),
        SightInfo("Sphinx von Gizeh", SphinxVonGizeh, 20.0),
        SightInfo("Brandenburger Tor", BrandenburgerTorIcon, 26.0),
        SightInfo("Christusstatue", JesusStatueIcon, 30.0),
        SightInfo("Leuchtturm Westerheversand", LightHouseIcon, 41.0),
        SightInfo("Schiefer Turm von Pisa", PisaTowerIcon, 55.0),
        SightInfo("Siegessäule", VictoryColumnIcon, 67.0),
        SightInfo("Freiheitsstatue", StatueOfLibertyIcon, 93.0),
        SightInfo("Münchner Frauenkirche", ChurchIcon, 100.0),
        SightInfo("Elbphilharmonie", ElbphilharmonieIcon, 110.0),
        SightInfo("Cheops-Pyramide", PyramidIcon, 137.0),
        SightInfo("Kölner Dom", KoelnerDomIcon, 157.0),
        SightInfo("Main Tower", MainTowerIcon, 200.0),
        SightInfo("Olympiaturm", OlympiaTowerIcon, 291.0),
        SightInfo("Eiffelturm", EifelTowerIcon, 300.0),
        SightInfo("Berliner Fernsehturm", BerlinFernsehTowerIcon, 368.0)
    )

    fun findNearestSight(targetHeight: Double): SightInfo {
        return sights.minBy { kotlin.math.abs(it.height - targetHeight) }
    }
}
