package com.nhat.cache

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

/**
 * General Preferences Helper class, used for storing preference values using the Preference API
 */
@Singleton
open class PreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private const val PREF_BUFFER_PACKAGE_NAME = "com.nhat.boilerplate.preferences"

        private const val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences =
            context.getSharedPreferences(PREF_BUFFER_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was cached
     */
    var lastCacheTime: Long
        get() = sharedPreferences.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = sharedPreferences.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

}
