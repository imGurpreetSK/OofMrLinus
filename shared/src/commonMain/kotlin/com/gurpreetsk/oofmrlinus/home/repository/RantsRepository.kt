package com.gurpreetsk.oofmrlinus.home.repository

import co.touchlab.kermit.Logger
import com.gurpreetsk.oofmrlinus.home.repository.internal.FileReader
import com.gurpreetsk.oofmrlinus.repository.model.Rant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

        if (rants.isEmpty()) {
            Result.failure(NoSuchElementException())
        } else {
            Result.success(rants.random())
        }
    } catch (e: Exception) {
        Logger.e(e.toString(), e)
        Result.failure(e)
    }
}
