package org.example.project

import kotlin.test.Test
import kotlin.reflect.full.declaredFunctions

class SharedLogicAndroidHostTest {

    @Test
    fun reflectLayers() {
        println("=== CIRCLE LAYER FUNCTIONS ===")
        try {
            val clazz = Class.forName("org.maplibre.compose.layers.CircleLayerKt")
            clazz.kotlin.declaredFunctions.forEach { f ->
                println("Function: ${f.name}")
                f.parameters.forEach { p ->
                    println("  Param: ${p.name} (type: ${p.type})")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        println("=== SYMBOL LAYER FUNCTIONS ===")
        try {
            val clazz = Class.forName("org.maplibre.compose.layers.SymbolLayerKt")
            clazz.kotlin.declaredFunctions.forEach { f ->
                println("Function: ${f.name}")
                f.parameters.forEach { p ->
                    println("  Param: ${p.name} (type: ${p.type})")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}