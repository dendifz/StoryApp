package com.dendi.storyapp.di

import android.content.Context
import com.dendi.storyapp.data.source.local.datastore.DataStoreManager
import com.dendi.storyapp.data.source.local.room.KeysDao
import com.dendi.storyapp.data.source.local.room.StoryDao
import com.dendi.storyapp.data.source.local.room.StoryDatabase
import com.dendi.storyapp.data.source.remote.auth.AuthServices
import com.dendi.storyapp.data.source.remote.story.StoryServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://story-api.dicoding.dev/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    fun provideStoryDao(appDatabase: StoryDatabase): StoryDao {
        return appDatabase.StoryDao()
    }

    @Provides
    fun provideKeysDao(appDatabase: StoryDatabase): KeysDao {
        return appDatabase.KeysDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = StoryDatabase.getDatabase(appContext)


    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthServices =
        retrofit.create(AuthServices::class.java)

    @Provides
    fun provideStoryService(retrofit: Retrofit): StoryServices =
        retrofit.create(StoryServices::class.java)

    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)

}