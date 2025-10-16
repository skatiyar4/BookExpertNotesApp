package com.example.bookexpertnotesapp.data.repository

import com.example.bookexpertnotesapp.data.datasource.NoteDataSource
import com.example.bookexpertnotesapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val dataSource: NoteDataSource): NoteRepository {

    override  fun getAllNotes(): Flow<List<Note>> = dataSource.getAllNotes()

    override suspend fun createNote(note: Note) = dataSource.createNote(note)

    override suspend fun updateNote(note: Note) = dataSource.updateNote(note)

    override suspend fun deleteNote(note: Note) = dataSource.deleteNote(note)

    override suspend fun getNote(id: Long): Note? = dataSource.getNote(id)
}
