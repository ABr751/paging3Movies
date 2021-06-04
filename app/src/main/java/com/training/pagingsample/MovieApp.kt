package com.training.pagingsample

import android.app.Application
import com.training.pagingsample.etc.di.viewModelModule
import com.training.pagingsample.etc.di.mockApiServiceModule
import com.training.pagingsample.etc.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MovieApp)
            modules(listOf( repositoryModule, viewModelModule,mockApiServiceModule))
        }
    }
}
