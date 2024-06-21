package com.jetbrains.kmpapp

import kotlin.test.Test
import kotlin.test.assertTrue

internal class CanaryTest {

    @Test
    fun `check setup is proper for common source set`() {
        assertTrue { 2 + 2 == 4 }
    }
}
