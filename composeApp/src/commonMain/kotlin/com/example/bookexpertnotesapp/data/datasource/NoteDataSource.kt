package com.example.bookexpertnotesapp.data.datasource

import com.example.bookexpertnotesapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun createNote(note: Note): Long
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun getNote(id: Long): Note?
}