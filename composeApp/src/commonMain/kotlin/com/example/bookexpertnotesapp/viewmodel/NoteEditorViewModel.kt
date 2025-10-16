package com.example.bookexpertnotesapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookexpertnotesapp.domain.model.Note
import com.example.bookexpertnotesapp.domain.usecases.CreateNoteUseCases
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoteEditorViewModel() : ViewModel(), KoinComponent {

    private val createNoteUseCase: CreateNoteUseCases by inject()
    private val _isShowToast = mutableStateOf("")
    val isShowToast: State<String> = _isShowToast


    fun saveNotes(note: Note, isEditNote: Boolean? = false) = viewModelScope.launch {
        if (isEditNote == true) {
            createNoteUseCase.updateNote(note)
        } else {
            createNoteUseCase.createNote(note)
        }
        _isShowToast.value = "Note saved successfully"
    }

}