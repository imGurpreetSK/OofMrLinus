package com.gurpreetsk.oofmrlinus.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gurpreetsk.oofmrlinus.repository.model.Rant
import com.gurpreetsk.oofmrlinus.screens.EmptyScreenContent

data object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: HomeScreenViewModel = getScreenModel()

        // TODO(gs) - maintain state in view model.
        var state: Result<Rant>? by remember { mutableStateOf(null) }
        LaunchedEffect(key1 = Unit) {
            state = viewModel.getRandomRant()
        }

        if (state == null) {
            EmptyScreenContent(Modifier.fillMaxSize())
        } else {
            if (state!!.isFailure) {
                EmptyScreenContent(Modifier.fillMaxSize())
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(state!!.getOrDefault(""))
                }
            }
        }
    }
}
