package com.windrad.util

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object GeoJsonUtils {
    fun buildRadiusCircleGeoJson(centerLat: Double, centerLon: Double, radiusKm: Double, points: Int = 64): String {
        val earthRadiusKm = 6371.0
        val d = radiusKm / earthRadiusKm
        val latR = centerLat * PI / 180.0
        val lonR = centerLon * PI / 180.0

        val coordinates = mutableListOf<String>()
        for (i in 0..points) {
            val angle = (360.0 / points) * i
            val angleR = angle * PI / 180.0

            val pLatR = kotlin.math.asin(sin(latR) * cos(d) + cos(latR) * sin(d) * cos(angleR))
            val pLonR = lonR + kotlin.math.atan2(
                sin(angleR) * sin(d) * cos(latR),
                cos(d) - sin(latR) * sin(pLatR)
            )

            val pLat = pLatR * 180.0 / PI
            val pLon = pLonR * 180.0 / PI
            coordinates.add("[$pLon, $pLat]")
        }

        return """
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "geometry": {
                "type": "Polygon",
                "coordinates": [
                  [
                    ${coordinates.joinToString(",\n                    ")}
                  ]
                ]
              }
            }
          ]
        }
        """.trimIndent()
    }
}
