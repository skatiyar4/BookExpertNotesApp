package com.example.bookexpertnotesapp.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import io.ktor.client.request.invoke
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSTimer
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle
import kotlinx.cinterop.*
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSData
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.writeToFile
import platform.PDFKit.PDFDocument
import platform.PDFKit.PDFView
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIGraphicsPDFRenderer
import platform.UIKit.UIView
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationActionPolicy
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKNavigationTypeLinkActivated
import platform.WebKit.WKScriptMessage
import platform.WebKit.WKScriptMessageHandlerProtocol
import platform.WebKit.WKUserContentController
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.darwin.NSObject

@Composable
actual fun pickDate(onDatePicked: (String) -> Unit) {

    val datePicker = UIDatePicker().apply {
        datePickerMode = UIDatePickerMode.UIDatePickerModeDate
        preferredDatePickerStyle =
            UIDatePickerStyle.UIDatePickerStyleWheels // Use the wheel-style picker
        translatesAutoresizingMaskIntoConstraints = false
    }

    val alertController = UIAlertController.alertControllerWithTitle(
        title = "Pick a Date",
        message = "\n\n\n\n\n\n\n",
        preferredStyle = UIAlertControllerStyleAlert
    )

    alertController.view.addSubview(datePicker)

    // Set constraints for the DatePicker
    datePicker.centerXAnchor.constraintEqualToAnchor(alertController.view.centerXAnchor).active =
        true
    datePicker.topAnchor.constraintEqualToAnchor(
        alertController.view.topAnchor, constant = 10.0
    ).active = true
    datePicker.widthAnchor.constraintEqualToAnchor(
        alertController.view.widthAnchor, constant = -20.0
    ).active = true

    alertController.addAction(
        UIAlertAction.actionWithTitle(
            title = "Done", style = UIAlertActionStyleDefault
        ) { _ ->
            val formatter = NSDateFormatter().apply {
                dateFormat = "dd-MM-yyyy"
            }
            val selectedDate = formatter.stringFromDate(datePicker.date)
            onDatePicked(selectedDate)
        })

    // Present the alert
    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
        alertController, animated = true, completion = null
    )
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun showToast(message: String) {
    // iOS does not have native toasts, so implement with UIAlertController or custom overlay
    val alert = UIAlertController.alertControllerWithTitle(
        null, message, UIAlertControllerStyleAlert
    )
    val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootVC?.presentViewController(alert, animated = true, completion = null)
    NSTimer.scheduledTimerWithTimeInterval(2.0, true) {
        alert.dismissViewControllerAnimated(true, null)
    }


}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CustomWebView(
    htmlContent: String, isLoading: ((Boolean) -> Unit)?, onUrlClicked: (url: String) -> Unit
) {
    UIKitView(
        factory = {
            val config = WKWebViewConfiguration()
            val contentController = WKUserContentController()

            contentController.addScriptMessageHandler(
                object : NSObject(), WKScriptMessageHandlerProtocol {
                    override fun userContentController(
                        userContentController: WKUserContentController,
                        didReceiveScriptMessage: WKScriptMessage
                    ) {
                        val msg = didReceiveScriptMessage.body as? String
                        msg?.let { onUrlClicked(it) }
                    }
                },
                name = "callback"
            )

            config.userContentController = contentController

            val webView = WKWebView(frame = CGRectZero.readValue(), configuration = config)
            webView.loadHTMLString(htmlContent, baseURL = null)
            webView
        },
        modifier = Modifier.fillMaxSize()
    )

}


@Composable
actual fun OpenPDFUrl(url: String) {

    var fileUrl by remember { mutableStateOf<NSURL?>(null) }

    // Trigger download once
    LaunchedEffect(url) {
        val remoteUrl = NSURL.URLWithString(url)
        remoteUrl?.let {
            val data = NSData.dataWithContentsOfURL(it)
            data?.let { downloadedData ->
                val tempDir = NSTemporaryDirectory()
                val localPath = tempDir + "/temp.pdf"
                val localUrl = NSURL.fileURLWithPath(localPath)
                downloadedData.writeToFile(localPath, true)
                fileUrl = localUrl
                println("Local URL : $localUrl")
            }
        }
    }

    if (fileUrl != null) {
        UIKitView(
            factory = {
                val container = UIView()
                val pdfView = PDFView()
                pdfView.autoScales = true
                val document = PDFDocument(fileUrl!!)
                pdfView.document = document

                pdfView.translatesAutoresizingMaskIntoConstraints = false
                container.addSubview(pdfView)

                NSLayoutConstraint.activateConstraints(
                    listOf(
                        pdfView.topAnchor.constraintEqualToAnchor(container.topAnchor),
                        pdfView.bottomAnchor.constraintEqualToAnchor(container.bottomAnchor),
                        pdfView.leadingAnchor.constraintEqualToAnchor(container.leadingAnchor),
                        pdfView.trailingAnchor.constraintEqualToAnchor(container.trailingAnchor)
                    )
                )

                container
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp),
            properties = UIKitInteropProperties(
                isInteractive = true,
                isNativeAccessibilityEnabled = true
            )
        )
    } else {
        // Optional loading state
        Text("Loading PDF...", modifier = Modifier.padding(16.dp))
    }
}