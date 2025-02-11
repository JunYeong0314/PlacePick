package com.jyproject.data.di

import com.jyproject.data.AppInterceptor
import com.jyproject.data.remote.service.auth.CheckNickService
import com.jyproject.data.remote.service.auth.CheckService
import com.jyproject.data.remote.service.auth.RegisterService
import com.jyproject.data.remote.service.place.GetPlaceInfoService
import com.jyproject.data.remote.service.place.SearchPlaceService
import com.jyproject.data.remote.service.seoulbike.GetSeoulBikeInfoService
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
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    companion object{
        const val BASE_URL = "http://192.168.0.9:4000"
        const val CONNECT_TIMEOUT_SECONDS = 10L
        const val READ_TIMEOUT_SECONDS = 10L
        const val WRITE_TIMEOUT_SECONDS = 10L
        const val SEOUL_URL = "http://openapi.seoul.go.kr:8088"
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SeoulRetrofit

    @BaseRetrofit
    @Singleton
    @Provides
    fun getOkHttpClient(interceptor: AppInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
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

    @SeoulRetrofit
    @Singleton
    @Provides
    fun getSeoulOkHttpClient(): OkHttpClient{
        return  OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @SeoulRetrofit
    @Singleton
    @Provides
    fun getSeoulInstance(@SeoulRetrofit okHttpClient: OkHttpClient): Retrofit{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(getSeoulOkHttpClient())
            .baseUrl(SEOUL_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideSignUpService(@BaseRetrofit retrofit: Retrofit): RegisterService {
        return retrofit.create(RegisterService::class.java)
    }

    @Singleton
    @Provides
    fun provideCheckService(@BaseRetrofit retrofit: Retrofit): CheckService {
        return retrofit.create(CheckService::class.java)
    }

    @Singleton
    @Provides
    fun provideCheckNickService(@BaseRetrofit retrofit: Retrofit): CheckNickService {
        return retrofit.create(CheckNickService::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchPlace(@BaseRetrofit retrofit: Retrofit): SearchPlaceService {
        return retrofit.create(SearchPlaceService::class.java)
    }

    @Singleton
    @Provides
    fun providePlaceInfo(@SeoulRetrofit retrofit: Retrofit): GetPlaceInfoService {
        return retrofit.create(GetPlaceInfoService::class.java)
    }

    @Singleton
    @Provides
    fun provideSeoulBikeInfo(@BaseRetrofit retrofit: Retrofit): GetSeoulBikeInfoService {
        return retrofit.create(GetSeoulBikeInfoService::class.java)
    }
}