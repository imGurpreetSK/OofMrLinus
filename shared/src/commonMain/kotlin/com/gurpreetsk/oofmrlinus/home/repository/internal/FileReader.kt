package com.gurpreetsk.oofmrlinus.home.repository.internal

import com.gurpreetsk.oofmrlinus.repository.model.Rants
import kotlinx.serialization.json.Json
import oofmrlinus.shared.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

typealias Line = String

internal fun interface FileReader {
    suspend fun read(): List<Line>
}

internal class RantsFileReader(
    private val json: Json
) : FileReader {

    @OptIn(ExperimentalResourceApi::class) // For Res.readBytes(...) usage.
    override suspend fun read(): List<Line> = try {
        val fileAsString = Res.readBytes("files/oofs.json").decodeToString()
        json.decodeFromString<Rants>(fileAsString).rants
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}
