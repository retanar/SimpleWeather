package com.vlprojects.weather.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.map

private val CITY_ID_KEY = intPreferencesKey("city_id")
private const val DEFAULT_CITY_ID = 13153       // London, UK

class CityPreferenceRepository(
    private val dataStore: DataStore<Preferences>
) {
    val cityPreference = dataStore.data.map { preferences ->
        CityPreference(preferences[CITY_ID_KEY] ?: DEFAULT_CITY_ID)
    }

    suspend fun updateCityId(id: Int) {
        dataStore.edit { preferences ->
            preferences[CITY_ID_KEY] = id
        }
    }
}