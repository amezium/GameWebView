package com.example.gamewebview.di

import com.example.gamewebview.presentation.fragments.GameFragment
import com.example.gamewebview.presentation.fragments.LoadFragment
import com.example.gamewebview.presentation.fragments.RecordFragment
import com.example.gamewebview.presentation.fragments.SettingFragment
import dagger.Component

@ApplicationScope
@Component(modules = [GamesModule::class, GamesModuleProvides::class, ViewModelModule::class])
interface ApplicationsComponent {

    fun inject(gameFragment: GameFragment)
    fun inject(recordFragment: RecordFragment)
    fun inject(settingsFragment: SettingFragment)
    fun inject(loadFragment: LoadFragment)
}