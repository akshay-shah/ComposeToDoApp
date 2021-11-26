package com.example.composetodoapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.composetodoapp.data.models.Priority
import com.example.composetodoapp.utils.Constants.PREFERENCE_KEY
import com.example.composetodoapp.utils.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataSore: DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext context: Context) {

    private object PreferenceKey {
        val sortKey = stringPreferencesKey(PREFERENCE_KEY)
    }

    private val dataStore = context.dataSore

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit {
            it[PreferenceKey.sortKey] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data
        .map {
            val sortState = it.get(PreferenceKey.sortKey) ?: Priority.NONE.name
            sortState
        }

}