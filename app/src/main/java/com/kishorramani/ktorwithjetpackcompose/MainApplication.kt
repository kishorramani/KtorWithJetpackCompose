package com.kishorramani.ktorwithjetpackcompose

import android.app.Application
import com.kishorramani.ktorwithjetpackcompose.utils.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}