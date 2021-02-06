package com.example.movieapp.utils

import android.app.Application
import com.example.movieapp.data.ApiClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApp: Application()
{
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@BaseApp)

            modules(
                listOf(
                    ApiClient.baseModule,
                    ApiClient.postersVM,
                    ApiClient.detailsVM
                )
            )

        }

    }
}