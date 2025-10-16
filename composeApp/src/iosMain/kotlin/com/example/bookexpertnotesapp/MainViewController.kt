package com.example.bookexpertnotesapp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.bookexpertnotesapp.di.initKoin


fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App() }

