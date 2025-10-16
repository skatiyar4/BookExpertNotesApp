package com.example.bookexpertnotesapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bookexpertnotesapp.composeapp.generated.resources.Res
import bookexpertnotesapp.composeapp.generated.resources.content_lbl
import bookexpertnotesapp.composeapp.generated.resources.create_note_lbl
import bookexpertnotesapp.composeapp.generated.resources.pick_a_date
import bookexpertnotesapp.composeapp.generated.resources.picked_date
import bookexpertnotesapp.composeapp.generated.resources.title_lbl
import com.example.bookexpertnotesapp.component.pickDate
import com.example.bookexpertnotesapp.component.showToast
import com.example.bookexpertnotesapp.currentDateDDMMYYYY
import com.example.bookexpertnotesapp.domain.model.Note
import com.example.bookexpertnotesapp.viewmodel.NoteEditorViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
@Preview
internal fun NoteEditorScreen(
    noteEditorViewModel: NoteEditorViewModel =  koinViewModel<NoteEditorViewModel>(),
    existingNote: Note? = null,
    onNoteSavedSuccess: () -> Unit
) {
    var title by remember { mutableStateOf(existingNote?.title ?: "") }
    var bodyHtml by remember {
        mutableStateOf(
            existingNote?.content ?: ("<h2>Welcome to KMP Notes</h2>\n" +
                    "<p>This is a <b>sample note</b> with HTML and interactive elements.</p>\n" +
                    "<button onclick=\"showInfo('Clicked on Button 1')\">Click Me 1</button>\n" +
                    "<a href=\"#\" onclick=\"showInfo('Link Clicked')\">Click This Link</a>\n" +
                    "<script>\n" +
                    "function showInfo(msg) {\n" +
                    "if (window.JavaScriptBridge) {\n" +
                    "window.JavaScriptBridge.postMessage(msg);\n" +
                    "}\n" +
                    "  if (window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.callback) {\n" +
                    "    window.webkit.messageHandlers.callback.postMessage(msg);\n" +
                    "  }\n" +
                    "}\n" +
                    "</script>"+"" )
        )
    }
    var createdDate by remember {
        mutableStateOf(
            existingNote?.createdDate ?: currentDateDDMMYYYY()
        )
    } //Current Date
    // Editing mode - if no existing note, start in editing mode for creating
    var isEditing by remember { mutableStateOf(existingNote == null) }
    var showDatePopup by remember { mutableStateOf(false) }
    val toastMessage by noteEditorViewModel.isShowToast
    //var selectedDate by remember { mutableStateOf("Select a date") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it},
            label = { Text(stringResource(Res.string.title_lbl)) },
            enabled = isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = bodyHtml,
            onValueChange = {bodyHtml = it },
            label = { Text(stringResource(Res.string.content_lbl)) },
            enabled = isEditing,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(vertical = 8.dp)
        )

        Text(text = stringResource(Res.string.picked_date, createdDate))
        Button(onClick = { showDatePopup = !showDatePopup }) {
            Text(stringResource(Res.string.pick_a_date))
        }

        if (showDatePopup) {
            pickDate { date -> createdDate = date }
        }

        if (toastMessage.isNotEmpty()) {
            showToast(toastMessage)
            onNoteSavedSuccess.invoke()
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (!isEditing) {
                Button(onClick = { isEditing = true }) {
                    Text("Edit Note")
                }
            } else {
                Button(onClick = {
                    noteEditorViewModel.saveNotes(Note(title = title, content = bodyHtml, createdDate = createdDate))
                }) {
                    Text(
                        stringResource(Res.string.create_note_lbl)
                    )
                }
            }
        }
    }
}



