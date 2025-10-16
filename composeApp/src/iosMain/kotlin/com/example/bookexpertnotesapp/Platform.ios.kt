package com.example.bookexpertnotesapp


import androidx.compose.runtime.Composable
import com.example.bookexpertnotesapp.data.AppDatabase
import com.example.bookexpertnotesapp.data.getDatabaseBuilder
import platform.Foundation.*
import platform.UIKit.*

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


actual fun getAppDatabase(): AppDatabase {
   return getDatabaseBuilder().build()
}