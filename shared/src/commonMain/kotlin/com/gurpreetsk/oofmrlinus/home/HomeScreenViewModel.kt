package com.gurpreetsk.oofmrlinus.home

import cafe.adriel.voyager.core.model.ScreenModel
import com.gurpreetsk.oofmrlinus.home.repository.RantsRepository
import com.gurpreetsk.oofmrlinus.repository.model.Rant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNot

internal class HomeScreenViewModel(
    private val repository: RantsRepository
) : ScreenModel {

    private val _rant = MutableStateFlow("")

    val rant: Flow<Rant> = _rant.filterNot { it.isBlank() }

    suspend fun getRandomRant() {
        var needRant = true
        var count = 0
        while (needRant && count < 3) {
            val result = repository.getRandom()
            if (result.isSuccess) {
                _rant.value = result.getOrThrow()
                needRant = false
            }
            count++
        }
    }
}
