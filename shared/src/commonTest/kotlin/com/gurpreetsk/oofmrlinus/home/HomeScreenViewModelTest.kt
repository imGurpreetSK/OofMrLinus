package com.gurpreetsk.oofmrlinus.home

import app.cash.turbine.test
import com.gurpreetsk.oofmrlinus.home.repository.RantsRepository
import com.gurpreetsk.oofmrlinus.repository.model.Rant
import com.gurpreetsk.oofmrlinus.utils.FakeDispatchersProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class HomeScreenViewModelTest {

    @Test
    fun `get random rant successfully`() = runTest {
        val repository = FakeRantsRepository()
        val viewModel = HomeScreenViewModel(repository, FakeDispatchersProvider())

        viewModel.post(HomeEvent.GetRant)

        viewModel.state.test {
            assertEquals(HomeModel("Rant 1"), awaitItem())
        }
    }

    @Test
    fun `multiple rants are emitted - one for each event`() = runTest {
        val repository = FakeRantsRepository()
        val viewModel = HomeScreenViewModel(repository, FakeDispatchersProvider())

        viewModel.state.test {
            assertEquals(HomeModel.uninitialized(), awaitItem())

            viewModel.post(HomeEvent.GetRant)
            assertEquals(HomeModel("Rant 1"), awaitItem())

            viewModel.post(HomeEvent.GetRant)
            assertEquals(HomeModel("Rant 2"), awaitItem())

            viewModel.post(HomeEvent.GetRant)
            assertEquals(HomeModel("Rant 3"), awaitItem())
        }
    }

    @Test
    fun `do nothing when getting random rant fails`() = runTest {
        val error = IllegalStateException()
        val repository = FakeRantsRepository().also { it.error = error }
        val viewModel = HomeScreenViewModel(repository, FakeDispatchersProvider())

        viewModel.post(HomeEvent.GetRant)

        viewModel.state.test {
            assertEquals(HomeModel(null, RantNotFoundException), awaitItem())
        }
    }

    private class FakeRantsRepository : RantsRepository {

        var error: Throwable? = null

        private val rants = listOf("Rant 1", "Rant 2", "Rant 3")
        private var invocationCount = 0

        override suspend fun getRandom(): Result<Rant> = if (error != null) {
            Result.failure(error!!)
        } else {
            Result.success(rants[invocationCount.coerceAtMost(2)]).also { invocationCount++ }
        }
    }
}
