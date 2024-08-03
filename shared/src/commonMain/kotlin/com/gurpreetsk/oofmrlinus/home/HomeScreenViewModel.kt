package com.gurpreetsk.oofmrlinus.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import co.touchlab.kermit.Logger
import com.gurpreetsk.oofmrlinus.base.DispatchersProvider
import com.gurpreetsk.oofmrlinus.base.State
import com.gurpreetsk.oofmrlinus.home.repository.RantsRepository
import com.gurpreetsk.oofmrlinus.repository.model.Rant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

internal class HomeScreenViewModel(
    repository: RantsRepository,
) : ScreenModel {

    val models: Flow<Rant> = repository
        .getRandom()
        .retry(3)
        .catch {
            Logger.e(it.message.orEmpty(), it)
            emit("")
        }
}
