package com.example.bookexpertnotesapp.domain.usecases

import com.example.bookexpertnotesapp.data.repository.NoteRepository
import com.example.bookexpertnotesapp.domain.model.Note
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class DeleteNoteUseCase : KoinComponent {
    private val noteRepository: NoteRepository by inject()
    suspend fun deleteNote(note: Note) = noteRepository.deleteNote(note)
}