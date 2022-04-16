package com.lucki.hamur.di

import com.lucki.hamur.data.repository.GameRepositoryImpl
import com.lucki.hamur.domain.GameRepository
import dagger.Binds
import dagger.Module

@Module
interface GamesModule {

    @ApplicationScope
    @Binds
    fun bindsRepository(impl: GameRepositoryImpl): GameRepository
}