package com.gurpreetsk.oofmrlinus.home

import cafe.adriel.voyager.core.model.ScreenModel
import com.gurpreetsk.oofmrlinus.home.repository.RantsRepository
import com.gurpreetsk.oofmrlinus.repository.model.Rant

private const val RETRY_THRESHOLD = 3

internal class HomeScreenViewModel(
    private val repository: RantsRepository
) : ScreenModel {

    suspend fun getRandomRant(): Result<Rant> {
        var retryCount = 0
        var result = repository.getRandom()
        while (result.isFailure && retryCount < RETRY_THRESHOLD) {
            result = repository.getRandom()
            retryCount++
        }
        
        return result
    }
}
