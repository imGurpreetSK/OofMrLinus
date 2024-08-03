package com.gurpreetsk.oofmrlinus.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersProvider {
    fun io(): CoroutineDispatcher
}

internal class DefaultDispatchersProvider : DispatchersProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.Default
}
