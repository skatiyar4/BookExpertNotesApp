package com.example.bookexpertnotesapp.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<Context> { androidContext() }
}