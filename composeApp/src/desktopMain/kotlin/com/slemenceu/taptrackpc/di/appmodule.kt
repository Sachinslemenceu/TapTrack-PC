package com.slemenceu.taptrackpc.di

import com.slemenceu.taptrackpc.taptrack.data.repository.ServerRepositoryImpl
import com.slemenceu.taptrackpc.taptrack.data.services.PcServer
import com.slemenceu.taptrackpc.taptrack.domain.ServerRepository
import com.slemenceu.taptrackpc.taptrack.ui.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { PcServer() }
    single<ServerRepository> { ServerRepositoryImpl(get()) }
    viewModelOf(::MainViewModel)
}