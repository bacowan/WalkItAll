package com.example.walkitall.utils

import android.content.Context
import java.util.Properties

fun loadProperties(context: Context): Properties {
    val properties = Properties()
    context.assets.open("keys.properties").use { properties.load(it) }
    return properties
}