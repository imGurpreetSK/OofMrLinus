package com.gurpreetsk.oofmrlinus.home

import com.gurpreetsk.oofmrlinus.repository.model.Rant

data class HomeModel(
    val rant: Rant?,
    val error: Throwable? = null
) {
    companion object {
        fun uninitialized() = HomeModel(null, null)
    }
}
