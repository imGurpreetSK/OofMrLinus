package com.gurpreetsk.oofmrlinus.repository

import com.gurpreetsk.oofmrlinus.repository.internal.FileReader
import com.gurpreetsk.oofmrlinus.repository.model.Rant

interface RantsRepository {
    suspend fun getRandom(): Result<Rant>
}

internal class FileBackedRantsRepository(
    private val reader: FileReader
) : RantsRepository {

    override suspend fun getRandom(): Result<Rant> = try {
        Result.success(reader.read().random())
    } catch (e: Exception) {
        Result.failure(IllegalStateException("Empty data set."))
    }
}
