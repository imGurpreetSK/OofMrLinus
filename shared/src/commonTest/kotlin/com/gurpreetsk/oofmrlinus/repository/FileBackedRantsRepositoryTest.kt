package com.gurpreetsk.oofmrlinus.repository

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue


internal class FileBackedRantsRepositoryTest {

    @Test
    fun `fetching random rant fails - empty data set`() = runTest {
        val repository: RantsRepository = FileBackedRantsRepository()

        val value = repository.getRandom()

        assertIs<IllegalStateException>(value.exceptionOrNull())
    }
}
