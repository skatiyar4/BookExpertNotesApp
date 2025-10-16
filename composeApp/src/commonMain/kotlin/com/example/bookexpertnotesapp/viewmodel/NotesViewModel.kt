package com.example.bookexpertnotesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookexpertnotesapp.domain.model.Note
import com.example.bookexpertnotesapp.domain.usecases.DeleteNoteUseCase
import com.example.bookexpertnotesapp.domain.usecases.GetNotesUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class NotesViewModel : ViewModel(), KoinComponent {
    private val getNotesUseCases: GetNotesUseCases by inject()
    private val deleteNoteUseCase: DeleteNoteUseCase by inject()

    /**
     * Get All the notes in DB
     * */
    val notesFlow: StateFlow<List<Note>> = getNotesUseCases.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    /**
     * Delete Note
     * */
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase.deleteNote(note)
        }
    }


}