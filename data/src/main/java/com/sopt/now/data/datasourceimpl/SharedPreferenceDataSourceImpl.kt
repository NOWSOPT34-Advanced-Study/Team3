package com.sopt.now.data.datasourceimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sopt.now.data.datasource.SharedPreferenceDataSource
import com.sopt.now.data.dto.UserDto
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SharedPreferenceDataSource {
    override var checkLogin: Boolean
        get() = runBlocking {
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preference ->
                    preference[stringPreferencesKey(CHECK_LOGIN)]?.toBoolean() ?: false
                }.first()
        }
        set(value) {
            runBlocking {
                dataStore.edit { preferences ->
                    preferences[stringPreferencesKey(CHECK_LOGIN)] =
                        value.toString()
                }
            }
        }

    override suspend fun saveUserInfo(userDto: UserDto?) {
        val json = Json.encodeToString(userDto)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(USER_INFO)] = json
        }
    }

    override suspend fun getUserInfo(): UserDto {
        val json = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preference ->
                preference[stringPreferencesKey(USER_INFO)] ?: ""
            }.first()

        if (json.isBlank()) return UserDto("", "", "", "")
        return Json.decodeFromString(json)
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(USER_INFO))
            preferences.remove(stringPreferencesKey(CHECK_LOGIN))
        }
    }

    companion object {
        const val USER_INFO = "userId"
        const val CHECK_LOGIN = "checkLogin"
    }
}
