package com.gurpreetsk.oofmrlinus.repository.model

import kotlinx.serialization.Serializable

typealias Rant = String

@Serializable
data class Rants(
    val rants: List<Rant>
)
