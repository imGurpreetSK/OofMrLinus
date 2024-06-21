package com.gurpreetsk.oofmrlinus.screens.detail

import cafe.adriel.voyager.core.model.ScreenModel
import com.gurpreetsk.oofmrlinus.data.MuseumObject
import com.gurpreetsk.oofmrlinus.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailScreenModel(private val museumRepository: MuseumRepository) : ScreenModel {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
