package com.gurpreetsk.oofmrlinus.di

import com.gurpreetsk.oofmrlinus.base.DefaultDispatchersProvider
import com.gurpreetsk.oofmrlinus.base.DispatchersProvider
import com.gurpreetsk.oofmrlinus.data.InMemoryMuseumStorage
import com.gurpreetsk.oofmrlinus.data.KtorMuseumApi
import com.gurpreetsk.oofmrlinus.data.MuseumApi
import com.gurpreetsk.oofmrlinus.data.MuseumRepository
import com.gurpreetsk.oofmrlinus.data.MuseumStorage
import com.gurpreetsk.oofmrlinus.home.HomeScreenViewModel
import com.gurpreetsk.oofmrlinus.home.repository.FileBackedRantsRepository
import com.gurpreetsk.oofmrlinus.home.repository.RantsRepository
import com.gurpreetsk.oofmrlinus.home.repository.internal.RantsFileReader
import com.gurpreetsk.oofmrlinus.screens.detail.DetailScreenModel
import com.gurpreetsk.oofmrlinus.screens.list.ListScreenModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(), get()).apply {
            initialize()
        }
    }

    single<RantsRepository> {
        val json = Json { ignoreUnknownKeys = true }
        FileBackedRantsRepository(RantsFileReader(json))
    }
}

val utilsModule = module {
    single<DispatchersProvider> { DefaultDispatchersProvider() }
}

val screenModelsModule = module {
    factoryOf(::ListScreenModel)
    factoryOf(::DetailScreenModel)
    factoryOf(::HomeScreenViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            utilsModule,
            screenModelsModule,
        )
    }
}
