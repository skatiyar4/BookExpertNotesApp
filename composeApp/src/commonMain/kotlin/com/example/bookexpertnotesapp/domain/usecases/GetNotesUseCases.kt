package com.example.bookexpertnotesapp.domain.usecases

import com.example.bookexpertnotesapp.data.repository.NoteRepository
import com.example.bookexpertnotesapp.domain.model.Note
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class GetNotesUseCases : KoinComponent {
    private val noteRepository: NoteRepository by inject()
     fun getAllNotes() = /*flowOf(emptyList<Note>())*/noteRepository.getAllNotes()
}