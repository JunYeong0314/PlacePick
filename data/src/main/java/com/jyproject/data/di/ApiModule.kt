package com.jyproject.data.di

import com.jyproject.data.AppInterceptor
import com.jyproject.data.remote.service.auth.CheckService
import com.jyproject.data.remote.service.auth.SignUpService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    companion object{
        const val BASE_URL = "http://192.168.0.4:4000"
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseRetrofit

    @BaseRetrofit
    @Singleton
    @Provides
    fun getOkHttpClient(interceptor: AppInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @BaseRetrofit
    @Singleton
    @Provides
    fun getInstance(@BaseRetrofit okHttpClient: OkHttpClient, interceptor: AppInterceptor): Retrofit{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(getOkHttpClient(interceptor))
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideSignUpService(@BaseRetrofit retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }

    @Singleton
    @Provides
    fun provideCheckService(@BaseRetrofit retrofit: Retrofit): CheckService {
        return retrofit.create(CheckService::class.java)
    }


}