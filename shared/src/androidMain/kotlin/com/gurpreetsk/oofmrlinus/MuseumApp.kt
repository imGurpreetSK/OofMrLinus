package com.gurpreetsk.oofmrlinus

import android.app.Application
import com.gurpreetsk.oofmrlinus.di.initKoin

class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
