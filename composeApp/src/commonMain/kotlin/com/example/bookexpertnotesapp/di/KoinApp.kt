package com.example.bookexpertnotesapp.di

import com.example.bookexpertnotesapp.getPlatform
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.module

/*expect fun platformModule(): Module*/
fun initKoin(config : KoinAppDeclaration? = null){
    startKoin {
        includes(config)
       modules(
           provideAppDatabase,
           provideDataSourceModule,
           provideRepositoryModule,
           provideUseCasesModule,
           provideViewModelModule,
           /*platformModule*/
       )

    }
}

