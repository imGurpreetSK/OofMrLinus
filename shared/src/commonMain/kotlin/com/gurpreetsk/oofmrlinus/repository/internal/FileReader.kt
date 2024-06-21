package com.gurpreetsk.oofmrlinus.repository.internal

typealias Line = String

internal fun interface FileReader {
    suspend fun read(): List<Line>
}
