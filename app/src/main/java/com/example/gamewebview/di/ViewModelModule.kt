package com.example.gamewebview.di

import androidx.lifecycle.ViewModel
import com.example.gamewebview.presentation.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(GameViewModel::class)
    @Binds
    fun bindsGifsViewModel(impl: GameViewModel): ViewModel
}

