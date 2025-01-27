package com.mydia.restaurantsmartqr.prefrences



import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

import  com.mydia.restaurantsmartqr.prefrences.dataStore as dataStore1

private val Context.dataStore by preferencesDataStore(
    name = PrefKey.PREFERENCE_NAME
)

@Singleton
class PreferencesServices @Inject constructor(
    private val context: Context
) {
    suspend fun putString(key: String, value: String?) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore1.edit { preferences ->
            preferences[preferencesKey] = value ?: ""
        }
    }

    suspend fun putInt(key: String, value: Int?) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore1.edit { preferences ->
            preferences[preferencesKey] = value ?: 0
        }
    }

    suspend fun putBoolean(key: String, value: Boolean?) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore1.edit { preferences ->
            preferences[preferencesKey] = value ?: false
        }
    }

    suspend fun getString(key: String): String {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore1.data.first()
        return preferences[preferencesKey] ?: ""
    }
    suspend fun getBaseUrl(key: String): String {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore1.data.first()
        return preferences[preferencesKey] ?: "https://vite.biz/foodcus/WebServices/V1"
    }

    suspend fun getInt(key: String): Int {
        val preferencesKey = intPreferencesKey(key)
        val preferences = context.dataStore1.data.first()
        return preferences[preferencesKey] ?: 0
    }

    suspend fun getBoolean(key: String): Boolean {
        val preferencesKey = booleanPreferencesKey(key)
        val preferences = context.dataStore1.data.first()
        return preferences[preferencesKey] ?: false
    }

    suspend fun <T> put( key: String, `object`: T) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore1.edit { preferences ->
            preferences[preferencesKey] = jsonString ?: ""
        }
    }

    suspend fun <T> get(key: String, modelClass: Class<T>): T? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore1.data.first()
        val value = preferences[preferencesKey] ?: ""
        return GsonBuilder().create().fromJson(value, modelClass)
    }

    suspend fun clearData() {
        context.dataStore1.edit { preferences ->
            preferences.clear()
        }
      //  appDB.clearAllTables()
    }


}
