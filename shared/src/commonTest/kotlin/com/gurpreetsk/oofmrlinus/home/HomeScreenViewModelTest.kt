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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get random rant successfully`() = runTest(UnconfinedTestDispatcher()) {
        val expectedRant = "Some rant here please."
        val repository = FakeRantsRepository().also { it.rant = expectedRant }
        val viewModel = HomeScreenViewModel(repository)

        viewModel.models.test {
            assertEquals(expectedRant, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `return empty string if getting random rant fails`() = runTest {
        val error = IllegalStateException()
        val repository = FakeRantsRepository().also { it.error = error }
        val viewModel = HomeScreenViewModel(repository)

        viewModel.models.test {
            assertEquals("", awaitItem())
            awaitComplete()
        }
    }

    private class FakeRantsRepository : RantsRepository {

        var rant: Rant? = null
        var error: Throwable = IllegalStateException("No rants available")

        override fun getRandom(): Flow<Rant> = flow {
            if (rant == null) {
                throw error
            } else {
                emit(rant!!)
            }
        }
    }
}
