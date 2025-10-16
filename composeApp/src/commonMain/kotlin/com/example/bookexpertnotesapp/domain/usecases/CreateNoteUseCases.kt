package com.example.bookexpertnotesapp.domain.usecases

import com.example.bookexpertnotesapp.data.repository.NoteRepository
import com.example.bookexpertnotesapp.domain.model.Note
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateNoteUseCases: KoinComponent {
    private val noteRepository: NoteRepository by inject()
    suspend fun createNote(note: Note) = noteRepository.createNote(note)
    suspend fun updateNote(note: Note) = noteRepository.updateNote(note)
}