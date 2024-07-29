package com.gurpreetsk.oofmrlinus.home.repository

import com.gurpreetsk.oofmrlinus.home.repository.internal.FileReader
import com.gurpreetsk.oofmrlinus.repository.model.Rant

interface RantsRepository {
    suspend fun getRandom(): Result<Rant>
}

internal class FileBackedRantsRepository(
    private val reader: FileReader
) : RantsRepository {

    private val rants = mutableListOf<Rant>()

    override suspend fun getRandom(): Result<Rant> = try {
        if (rants.isEmpty()) {
            rants.addAll(reader.read())
        }
        Result.success(rants.random())
    } catch (e: Exception) {
        Result.failure(IllegalStateException("Empty data set."))
    }
}
