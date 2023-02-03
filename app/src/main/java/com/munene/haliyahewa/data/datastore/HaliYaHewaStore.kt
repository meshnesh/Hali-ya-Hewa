package com.munene.haliyahewa.data.datastore

import android.content.Context

object HaliYaHewaStore {

    private lateinit var dataStore: DataStoreUtils

    fun init(context: Context) {
        dataStore = DataStoreUtils
        dataStore.init(context)
    }

    private const val CURRENT_LOCATION = "current_location"

    var currentLocation: String
        get() = dataStore.getSyncData(CURRENT_LOCATION, "")
        set(s) {
            dataStore.putSyncData(CURRENT_LOCATION, s)
        }
}
