package com.gurpreetsk.oofmrlinus.home

import com.gurpreetsk.oofmrlinus.home.repository.RantsRepository
import com.gurpreetsk.oofmrlinus.repository.model.Rant
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class HomeScreenViewModelTest {

    @Test
    fun `get random rant successfully`() = runTest {
        val expectedRant = "Some rant here please."
        val repository = FakeRantsRepository().also { it.rant = expectedRant }
        val viewModel = HomeScreenViewModel(repository)

        val randomRant = viewModel.getRandomRant()

        assertEquals(randomRant, Result.success(expectedRant))
    }

    @Test
    fun `return failure if getting random rant fails`() = runTest {
        val repository = FakeRantsRepository()
        val viewModel = HomeScreenViewModel(repository)

        val result = viewModel.getRandomRant()

        assertTrue(result.isFailure)
    }

    @Test
    fun `retry if getting random rant fails`() = runTest {
        val repository = FakeRantsRepository(recoversFromError = true)
        val viewModel = HomeScreenViewModel(repository)

        val result = viewModel.getRandomRant()

        assertEquals(result, Result.success("Rant"))
    }

    private class FakeRantsRepository(
        private val recoversFromError: Boolean = false
    ) : RantsRepository {

        var rant: Rant? = null
        private var invocationCount = 0

        override suspend fun getRandom(): Result<Rant> {
            if (recoversFromError && invocationCount > 0) {
                return Result.success("Rant")
            }

            return if (rant == null) {
                Result.failure(IllegalStateException("No rants available"))
            } else {
                Result.success(rant!!)
            }.also {
                invocationCount++
            }
        }
    }
}
