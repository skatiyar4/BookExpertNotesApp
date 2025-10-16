package com.example.bookexpertnotesapp

import androidx.compose.runtime.Composable
import com.example.bookexpertnotesapp.data.AppDatabase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


expect fun getAppDatabase(): AppDatabase

