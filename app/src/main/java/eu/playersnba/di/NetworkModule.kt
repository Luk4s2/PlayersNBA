package eu.playersnba.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.playersnba.data.remote.NbaApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import eu.playersnba.BuildConfig
import eu.playersnba.utils.Constants
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                Log.d("API", "Auth Header: Bearer ${BuildConfig.NBA_API_KEY}")
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", BuildConfig.NBA_API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNbaApiService(retrofit: Retrofit): NbaApiService {
        return retrofit.create(NbaApiService::class.java)
    }
}