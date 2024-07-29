package com.gurpreetsk.oofmrlinus.repository.internal

import com.gurpreetsk.oofmrlinus.home.repository.internal.RantsFileReader
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertTrue

internal class RantsFileReaderTest {

    @Test
    fun `should read rants file from resources`() = runTest {
        val json = Json {
            this.ignoreUnknownKeys = true
            this.prettyPrint = true
        }
        val reader = RantsFileReader(json)

        val contents = reader.read()

        assertTrue(contents.size == 312)
    }
}
