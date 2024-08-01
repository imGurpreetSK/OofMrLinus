package com.gurpreetsk.oofmrlinus.home.repository

import co.touchlab.kermit.Logger
import com.gurpreetsk.oofmrlinus.home.repository.internal.FileReader
import com.gurpreetsk.oofmrlinus.repository.model.Rant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface RantsRepository {
    fun getRandom(): Flow<Rant>
}

internal class FileBackedRantsRepository(
    private val reader: FileReader
) : RantsRepository {

    private val rants = mutableListOf<Rant>()

    override fun getRandom(): Flow<Rant> = flow {
        try {
            if (rants.isEmpty()) {
                rants.addAll(reader.read())
            }
            emit(rants.random())
        } catch (e: Exception) {
            Logger.e(e.toString(), e)
            throw e
        }
    }
}
