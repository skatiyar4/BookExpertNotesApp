package com.example.bookexpertnotesapp.di

import com.example.bookexpertnotesapp.data.datasource.NoteDataSource
import com.example.bookexpertnotesapp.data.datasource.NoteDataSourceImpl
import com.example.bookexpertnotesapp.data.repository.NoteRepository
import com.example.bookexpertnotesapp.data.repository.NoteRepositoryImpl
import com.example.bookexpertnotesapp.domain.usecases.CreateNoteUseCases
import com.example.bookexpertnotesapp.domain.usecases.DeleteNoteUseCase
import com.example.bookexpertnotesapp.domain.usecases.GetNotesUseCases
import com.example.bookexpertnotesapp.getAppDatabase
import com.example.bookexpertnotesapp.viewmodel.NoteEditorViewModel
import com.example.bookexpertnotesapp.viewmodel.NotesViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


val provideAppDatabase = module {
    singleOf(::getAppDatabase)
}

val provideDataSourceModule = module {
    singleOf(::NoteDataSourceImpl).bind(NoteDataSource::class)
}

val provideRepositoryModule = module {
    singleOf(::NoteRepositoryImpl).bind(NoteRepository::class)
}

val provideUseCasesModule = module {
    singleOf(::CreateNoteUseCases)
    singleOf(::GetNotesUseCases)
    singleOf(::DeleteNoteUseCase)

}

val provideViewModelModule = module {
    viewModel {
        NoteEditorViewModel()
    }
    viewModel {
        NotesViewModel()
    }
}


