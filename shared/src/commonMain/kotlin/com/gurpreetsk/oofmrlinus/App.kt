package com.gurpreetsk.oofmrlinus

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.gurpreetsk.oofmrlinus.home.HomeScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(HomeScreen)
    }
}
