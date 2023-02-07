package com.munene.haliyahewa

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.cioccarellia.ksprefs.BuildConfig
import com.google.android.libraries.places.api.Places
import com.munene.haliyahewa.data.datastore.HaliYaHewaStore
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class HaliYaHewa : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        HaliYaHewaStore.init(applicationContext)
        initTimberDebugTree()

        // Initialize the SDK with the Google Maps Platform API key can be import from the BuildConfig
        Places.initialize(this, "AIzaSyAq-VfBoQCTHVyeIORDXOrlY4JXH0yrZc4")
    }

    /** Initialize Timber debug tree. */
    private fun initTimberDebugTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setMinimumLoggingLevel(Log.INFO)
        .setWorkerFactory(workerFactory)
        .build()
}