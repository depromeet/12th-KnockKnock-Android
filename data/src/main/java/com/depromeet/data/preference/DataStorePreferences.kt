package com.depromeet.data.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.depromeet.data.R
import kotlinx.coroutines.flow.first

class DataStorePreferences(
    private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(name = "knockknock_prefs")

    // Token Variance
    private val accessTokenPreference = stringPreferencesKey("X-ACCESS-TOKEN")
    private val refreshTokenPreference = stringPreferencesKey("refresh-token")
    private val fcmTokenPreference = stringPreferencesKey("FCM-TOKEN")

    suspend fun setOauthToken(accessToken: String?, refreshToken: String?) {
        accessToken?.let {
            context.dataStore.edit { preference ->
                preference[accessTokenPreference] = it
            }
        }
        refreshToken?.let {
            context.dataStore.edit { preference ->
                preference[refreshTokenPreference] = it
            }
        }
    }

    suspend fun getAccessToken(): String? {
        return context.dataStore.data.first().let {
            it[accessTokenPreference]
        }
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.first().let {
            it[refreshTokenPreference]
        }
    }

    suspend fun removeOauthToken() {
        context.dataStore.edit { preference ->
            preference[accessTokenPreference] = ""
            preference[refreshTokenPreference] = ""
        }
    }

    suspend fun setFcmToken(token: String?) {
        token?.let {
            context.dataStore.edit { preference ->
                preference[fcmTokenPreference] = it
            }
        }
    }

    suspend fun getFcmToken(): String? {
        return context.dataStore.data.first().let {
            it[fcmTokenPreference]
        }
    }
}