package com.gurpreetsk.oofmrlinus.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gurpreetsk.oofmrlinus.base.DispatchersProvider
import com.gurpreetsk.oofmrlinus.home.repository.RantsRepository
import com.gurpreetsk.oofmrlinus.repository.model.Rant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeScreenViewModel(
    private val repository: RantsRepository,
    dispatchersProvider: DispatchersProvider
) : ScreenModel {

    private val screenModelScope = CoroutineScope(
        SupervisorJob() + dispatchersProvider.main + CoroutineName(TAG + "Coroutine")
    )

    private val _state: MutableStateFlow<HomeModel> = MutableStateFlow(HomeModel.uninitialized())

    val state: StateFlow<HomeModel> =
        _state.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), HomeModel.uninitialized())

    fun post(event: HomeEvent) {
        when (event) {
            HomeEvent.GetRant -> getRandomRant()
        }
    }

    private fun getRandomRant() {
        suspend fun getRandomRant(): Rant? {
            repeat(3) {
                val result = repository.getRandom()
                if (result.isSuccess) {
                    return result.getOrThrow()
                }
            }

            return null
        }

        screenModelScope.launch {
            val rant = getRandomRant()
            val hasError = rant == null
            _state.update {
                if (hasError) {
                    HomeModel(null, RantNotFoundException)
                } else {
                    HomeModel(rant)
                }
            }
        }
    }

    companion object {
        const val TAG = "HomeScreenViewModel"
    }
}

data object RantNotFoundException : Exception("Couldn't load rant.")
