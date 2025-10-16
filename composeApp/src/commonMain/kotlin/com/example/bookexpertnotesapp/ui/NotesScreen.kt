package com.example.bookexpertnotesapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookexpertnotesapp.composeapp.generated.resources.Res
import bookexpertnotesapp.composeapp.generated.resources.baseline_delete_outline_24
import bookexpertnotesapp.composeapp.generated.resources.delete_lbl
import bookexpertnotesapp.composeapp.generated.resources.no_notes_msg
import com.example.bookexpertnotesapp.domain.model.Note
import com.example.bookexpertnotesapp.viewmodel.NotesViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
internal fun NotesScreen(
    onNoteViewClick: (note: Note) -> Unit,
    onNoteEditClick: (note: Note) -> Unit,
) {
    val notesViewModel: NotesViewModel = koinViewModel<NotesViewModel>()
    val notes by notesViewModel.notesFlow.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    println("Notes Size - ${notes.size}")
    Column(modifier = Modifier.fillMaxSize()) {
        if (notes.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState
            ) {
                items(notes, key = { item -> item.hashCode() }) { note ->
                    NoteItem(
                        note = note,
                        onDelete = { notesViewModel.deleteNote(note) },
                        onClick = { onNoteViewClick.invoke(note)},
                        onEdit = { onNoteEditClick.invoke(note)}
                    )
                }
            }
        } else {
            NoDataPlaceHolder(
                modifier = Modifier.weight(1f),
                stringResource(Res.string.no_notes_msg)
            )
        }
    }
}

@Composable
internal fun NoDataPlaceHolder(modifier: Modifier, placeHolderTxt: String) {
    Box(
        modifier = modifier.fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = placeHolderTxt,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
internal fun NoteItem(
    note: Note,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(note.title, style = MaterialTheme.typography.titleMedium)
                Text(note.content.take(100), maxLines = 2, overflow = TextOverflow.Ellipsis)


            }
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(Res.string.delete_lbl)
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    painterResource(Res.drawable.baseline_delete_outline_24),
                    contentDescription = stringResource(Res.string.delete_lbl)
                )
            }
        }
    }
}


