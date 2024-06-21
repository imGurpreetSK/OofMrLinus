package com.gurpreetsk.oofmrlinus.repository

import com.gurpreetsk.oofmrlinus.repository.model.Rant

interface RantsRepository {
    suspend fun getRandom(): Result<Rant>
}
