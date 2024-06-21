package com.gurpreetsk.oofmrlinus.repository

import com.gurpreetsk.oofmrlinus.repository.model.Rant

interface RantsRepository {
    suspend fun getRandom(): Result<Rant>
}

internal class FileBackedRantsRepository : RantsRepository {

    override suspend fun getRandom(): Result<Rant> {
        return Result.failure(IllegalStateException("Empty data set."))
    }
}
