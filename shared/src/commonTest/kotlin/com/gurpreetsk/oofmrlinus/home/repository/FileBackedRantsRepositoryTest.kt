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
        val repository: RantsRepository = FileBackedRantsRepository { throw IllegalStateException("File absent.") }

        repository.getRandom().test {
            assertIs<IllegalStateException>(awaitError())
        }
    }

    @Test
    fun `fetching random rant succeeds - empty data set`() = runTest {
        val repository: RantsRepository = FileBackedRantsRepository { emptyList() }

        repository.getRandom().test {
            assertIs<NoSuchElementException>(awaitError())
        }
    }

    @Test
    fun `fetching random rant succeeds - single item in data set`() = runTest {
        val repository: RantsRepository = FileBackedRantsRepository {
            listOf("Some sharp comment.")
        }

        repository.getRandom().test {
            val expected = "Some sharp comment."
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `fetching random rant succeeds`() = runTest {
        val rants = listOf("Some sharp comment 1.", "Some sharp comment 2.", "Some sharp comment 3.")
        val repository: RantsRepository = FileBackedRantsRepository { rants }

        repository.getRandom().test {
            assertContains(rants, awaitItem())
            awaitComplete()
        }
    }
}
