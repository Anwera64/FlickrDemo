package com.example.data

import com.example.data.restservices.FlickrPhotosRestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        return OkHttpClient.Builder()
            .connectTimeout(
                timeout = 5,
                unit = TimeUnit.SECONDS
            )
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.flickr.com/services/rest/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideFlickrService(retrofit: Retrofit): FlickrPhotosRestService {
        return retrofit.create(FlickrPhotosRestService::class.java)
    }
}