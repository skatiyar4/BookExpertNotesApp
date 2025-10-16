package com.example.bookexpertnotesapp.component

import androidx.compose.runtime.Composable

@Composable
expect fun pickDate(onDatePicked: (String) -> Unit)

@Composable
expect fun showToast(message: String)


@Composable
expect fun CustomWebView(htmlContent: String, isLoading:( (isLoading: Boolean) -> Unit)?, onUrlClicked: (url: String) -> Unit)

/*
* https://medium.com/@adman.shadman/implementing-webview-in-kotlin-multiplatform-97341cb4eb56
*
* */

@Composable
expect fun OpenPDFUrl(url: String)