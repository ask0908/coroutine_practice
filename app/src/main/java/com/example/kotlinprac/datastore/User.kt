package com.example.kotlinprac.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/* dataStore : preferencesDataStore에서 만든 DataStore<Preferences>의 인스턴스 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")