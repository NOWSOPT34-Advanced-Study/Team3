package com.sopt.now.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {
    private const val PREFERENCE_NAME = "chanu_dataStore"
    private val Context.datastore by preferencesDataStore(
        name = PREFERENCE_NAME
    )

    @Provides
    @Singleton
    fun providesLocalPreferences(
        @ApplicationContext context: Context
    ) = context.datastore
}
