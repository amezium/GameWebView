package com.example.gamewebview.di

import com.example.gamewebview.data.repository.GameRepositoryImpl
import com.example.gamewebview.domain.GameRepository
import dagger.Binds
import dagger.Module

@Module
interface GamesModule {

    @ApplicationScope
    @Binds
    fun bindsRepository(impl: GameRepositoryImpl): GameRepository
}