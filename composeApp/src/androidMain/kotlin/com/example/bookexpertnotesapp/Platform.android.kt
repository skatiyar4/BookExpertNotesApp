package com.example.bookexpertnotesapp


import android.content.Context
import android.os.Build
import android.app.DatePickerDialog
import androidx.compose.ui.platform.LocalContext
import com.example.bookexpertnotesapp.data.AppDatabase
import com.example.bookexpertnotesapp.data.getDatabaseBuilder
import org.koin.core.component.KoinComponent
import java.util.Calendar



class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


actual fun getAppDatabase(): AppDatabase{
    return getDatabaseBuilder(AndroidContextHolder.context).build()
}
