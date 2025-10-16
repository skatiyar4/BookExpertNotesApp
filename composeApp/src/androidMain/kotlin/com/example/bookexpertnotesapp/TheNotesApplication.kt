package com.example.bookexpertnotesapp

import android.app.Application
import com.example.bookexpertnotesapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class TheNotesApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        AndroidContextHolder.context = applicationContext
        initKoin()
    }
}