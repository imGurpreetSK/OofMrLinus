package com.gurpreetsk.oofmrlinus.repository

import com.gurpreetsk.oofmrlinus.home.repository.FileBackedRantsRepository
import com.gurpreetsk.oofmrlinus.home.repository.RantsRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertIs


internal class FileBackedRantsRepositoryTest {

    @Test
    fun `fetching random rant fails - file unavailable`() = runTest {
        val repository: RantsRepository = FileBackedRantsRepository { throw IllegalStateException("File absent.") }

        val value = repository.getRandom()

        assertIs<IllegalStateException>(value.exceptionOrNull())
    }

    @Test
    fun `fetching random rant succeeds - single item in data set`() = runTest {
        val repository: RantsRepository = FileBackedRantsRepository {
            listOf("Some sharp comment.")
        }

        val value = repository.getRandom()

        val expected = Result.success("Some sharp comment.")
        assertEquals(expected, value)
    }

    @Test
    fun `fetching random rant succeeds`() = runTest {
        val rants = listOf("Some sharp comment 1.", "Some sharp comment 2.", "Some sharp comment 3.")
        val repository: RantsRepository = FileBackedRantsRepository { rants }

        val value = repository.getRandom()

        assertContains(rants, value.getOrNull())
    }
}
