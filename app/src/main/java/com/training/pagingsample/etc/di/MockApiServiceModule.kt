package com.training.pagingsample.etc.di

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.training.pagingsample.BuildConfig
import com.training.pagingsample.data.network.Api
import com.training.pagingsample.data.network.MockApiService
import com.training.pagingsample.data.network.MovieAppService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

val mockApiServiceModule = module {
    single { createMockMovieAppService(get()) }
    single { serializerProvider() }
    single { provideRetrofit(get()) }
    single { behaviorProvider() }
    single { provideMockService(get(), get(), get(), androidApplication()) }
}

fun createMockMovieAppService(
    api: Api
): MovieAppService = MovieAppService(api)

fun serializerProvider(): Gson {
    return GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create()
}

fun provideRetrofit(serializer: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(serializer))
        .build()
}


fun behaviorProvider(): NetworkBehavior {
    val behavior = NetworkBehavior.create()
    behavior.setDelay(500, TimeUnit.MILLISECONDS)
    behavior.setErrorPercent(0)
    behavior.setVariancePercent(0)
    return behavior
}


fun provideMockService(
    retrofit: Retrofit,
    behavior: NetworkBehavior,
    serializer: Gson,
    app: Application
): Api {
    val mock = MockRetrofit.Builder(retrofit)
        .networkBehavior(behavior)
        .build()
    val delegate = mock.create(Api::class.java)
    return MockApiService(delegate, serializer, app)
}