package com.gurpreetsk.oofmrlinus.utils

import com.gurpreetsk.oofmrlinus.base.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
internal class FakeDispatchersProvider(
    dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : DispatchersProvider {
    override val io: CoroutineDispatcher = dispatcher
    override val main: CoroutineDispatcher = dispatcher
    override val default: CoroutineDispatcher = dispatcher
}
