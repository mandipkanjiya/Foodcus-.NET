package com.mydia.restaurantsmartqr.di



import android.content.Context

import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.util.Network
import com.mydia.restaurantsmartqr.util.NetworkConnectivity

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providePreferenceData(@ApplicationContext context: Context) = PreferencesServices(context = context)

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return Network(context)
    }


}