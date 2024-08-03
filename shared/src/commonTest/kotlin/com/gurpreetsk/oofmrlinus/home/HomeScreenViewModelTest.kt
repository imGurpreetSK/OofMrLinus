package com.gurpreetsk.oofmrlinus.home

import app.cash.turbine.test
import com.gurpreetsk.oofmrlinus.home.repository.RantsRepository
import com.gurpreetsk.oofmrlinus.repository.model.Rant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class HomeScreenViewModelTest {

    @Test
    fun `get random rant successfully`() = runTest {
        val expectedRant = "Some rant here please."
        val repository = FakeRantsRepository().also { it.rant = expectedRant }
        val viewModel = HomeScreenViewModel(repository)

        viewModel.getRandomRant()
        viewModel.rant.test {
            assertEquals(expectedRant, awaitItem())
        }
    }

    @Test
    fun `return empty string if getting random rant fails`() = runTest {
        val error = IllegalStateException()
        val repository = FakeRantsRepository().also { it.error = error }
        val viewModel = HomeScreenViewModel(repository)

        viewModel.getRandomRant()
        viewModel.rant.test {
            expectNoEvents()
        }
    }

    private class FakeRantsRepository : RantsRepository {

        var rant: Rant? = null
        var error: Throwable = IllegalStateException("No rants available")

        override suspend fun getRandom(): Result<Rant> = if (rant == null) {
            Result.failure(error)
        } else {
            Result.success(rant!!)
        }
    }
}
