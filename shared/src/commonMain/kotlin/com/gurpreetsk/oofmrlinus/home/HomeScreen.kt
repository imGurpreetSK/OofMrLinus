package com.gurpreetsk.oofmrlinus.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import kotlinx.coroutines.flow.MutableSharedFlow

internal data object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: HomeScreenViewModel = getScreenModel()

        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.post(HomeEvent.GetRant)
        }

        val scrollState = rememberScrollState()
        val interactionSource = remember { MutableInteractionSource() }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clickable(interactionSource, null) {
                    viewModel.post(HomeEvent.GetRant)
                }
        ) {
            Text(
                text = state.rant ?: state.error?.message ?: "",
                modifier = Modifier.verticalScroll(scrollState)
            )
        }
    }
}
