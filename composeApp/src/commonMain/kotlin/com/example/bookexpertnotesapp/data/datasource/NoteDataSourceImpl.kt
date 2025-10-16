package com.example.bookexpertnotesapp.data.datasource

import com.example.bookexpertnotesapp.data.AppDatabase
import com.example.bookexpertnotesapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

class NoteDataSourceImpl (private val appDatabase: AppDatabase) : NoteDataSource {
    override fun getAllNotes(): Flow<List<Note>> = appDatabase.noteDao().getAllNotes()

    override suspend fun createNote(note: Note) = appDatabase.noteDao().insertNote(note)

    override suspend fun deleteNote(note: Note) = appDatabase.noteDao().deleteNote(note)

    override suspend fun updateNote(note: Note) = appDatabase.noteDao().updateNote(note)

    override suspend fun getNote(id: Long) = appDatabase.noteDao().getNoteById(id)
}