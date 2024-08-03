package com.gurpreetsk.oofmrlinus.home.repository

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertIs


internal class FileBackedRantsRepositoryTest {

    @Test
    fun `fetching random rant fails - file unavailable`() = runTest {
        val exception = IllegalStateException("File absent.")
        val repository: RantsRepository = FileBackedRantsRepository { throw exception }

        val result = repository.getRandom()

        assertEquals(result, Result.failure(exception))
    }

    @Test
    fun `fetching random rant succeeds - empty data set`() = runTest {
        val repository: RantsRepository = FileBackedRantsRepository { emptyList() }

        val result = repository.getRandom()

        assertIs<NoSuchElementException>(result.exceptionOrNull())
    }

    @Test
    fun `fetching random rant succeeds - single item in data set`() = runTest {
        val repository: RantsRepository = FileBackedRantsRepository {
            listOf("Some sharp comment.")
        }

        val result = repository.getRandom()

        val expected = "Some sharp comment."
        assertEquals(result, Result.success(expected))
    }

    @Test
    fun `fetching random rant succeeds`() = runTest {
        val rants = listOf("Some sharp comment 1.", "Some sharp comment 2.", "Some sharp comment 3.")
        val repository: RantsRepository = FileBackedRantsRepository { rants }

        val result = repository.getRandom()

        assertContains(rants, result.getOrNull())
    }
}
