package com.example.wilber_p2_ap2.di

import com.example.wilber_p2_ap2.data.gastosApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn( SingletonComponent::class)
object AppModule {

    @Provides
        @Singleton
        fun provideDocumentosApi(moshi: Moshi, ): gastosApi {
            return Retrofit.Builder()
                .baseUrl("https://sag-api.azurewebsites.net/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(gastosApi::class.java)
        }
}