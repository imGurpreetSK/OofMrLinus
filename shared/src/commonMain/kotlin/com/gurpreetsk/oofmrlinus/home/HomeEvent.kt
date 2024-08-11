package com.gurpreetsk.oofmrlinus.home

sealed interface HomeEvent {
    data object GetRant : HomeEvent
}
