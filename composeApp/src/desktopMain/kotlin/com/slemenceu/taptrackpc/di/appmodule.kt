package com.slemenceu.taptrackpc.di

import com.slemenceu.taptrackpc.taptrack.data.repository.ServerRepoTest
import com.slemenceu.taptrackpc.taptrack.data.repository.ServerRepositoryImpl
import com.slemenceu.taptrackpc.taptrack.data.services.UDPServerService
import com.slemenceu.taptrackpc.taptrack.domain.ServerRepository
import com.slemenceu.taptrackpc.taptrack.ui.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { UDPServerService() }
    single<ServerRepository> { ServerRepositoryImpl(get()) }
    viewModelOf(::MainViewModel)
}