package com.example.bookexpertnotesapp.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import bookexpertnotesapp.composeapp.generated.resources.Res
import bookexpertnotesapp.composeapp.generated.resources.link_btn_msg
import bookexpertnotesapp.composeapp.generated.resources.ok_lbl
import com.example.bookexpertnotesapp.component.CustomWebView
import com.example.bookexpertnotesapp.domain.model.Note
import org.jetbrains.compose.resources.stringResource


@Composable
fun ViewNoteScreen(note: Note) {
    var showDialog by remember { mutableStateOf(Pair<Boolean, String>(false, "")) }
    if (showDialog.first) {
        AlertDialog(
            onDismissRequest = { showDialog = Pair(false, "") },
            title = {
                Text(
                    text = stringResource(Res.string.link_btn_msg),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = { Text(showDialog.second, style = MaterialTheme.typography.bodyLarge) },
            confirmButton = {
                TextButton(onClick = { showDialog = Pair(false, "") }) {
                    Text(
                        stringResource(
                            Res.string.ok_lbl
                        )
                    )
                }
            }
        )
    }
    CustomWebView(
        note.content, isLoading = null,
        onUrlClicked = { url ->
            showDialog = Pair(true, url)
        },
    )
}
