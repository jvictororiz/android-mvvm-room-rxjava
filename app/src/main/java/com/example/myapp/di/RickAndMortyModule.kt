package com.example.myapp.di

import android.content.Context
import androidx.room.Room
import com.example.myapp.BuildConfig
import com.example.myapp.repository.local.database.RickAndMortyDatabase
import com.example.myapp.repository.network.service.RickAndMortyService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RickAndMortyModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RickAndMortyUrl

    @Provides
    fun provideRetrofit(@RickAndMortyUrl baseUrl: String): Retrofit {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .callTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

        val gsonConfig = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gsonConfig))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideRickAndMortyRetrofitService(retrofit: Retrofit): RickAndMortyService =
        retrofit.create(RickAndMortyService::class.java)

    @RickAndMortyUrl
    @Provides
    fun provideBaseUrl() = BuildConfig.RICK_AND_MORTY_API_URL

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RickAndMortyDatabase =
        Room.databaseBuilder(context, RickAndMortyDatabase::class.java, "RickMortyDB")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideRickAndMortyDao(db: RickAndMortyDatabase) = db.rickAndMortyDao()

}