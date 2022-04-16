package com.lucki.hamur.di

import com.lucki.hamur.presentation.fragments.GameFragment
import com.lucki.hamur.presentation.fragments.LoadFragment
import com.lucki.hamur.presentation.fragments.RecordFragment
import com.lucki.hamur.presentation.fragments.SettingFragment
import dagger.Component

@ApplicationScope
@Component(modules = [GamesModule::class, GamesModuleProvides::class, ViewModelModule::class])
interface ApplicationsComponent {

    fun inject(gameFragment: GameFragment)
    fun inject(recordFragment: RecordFragment)
    fun inject(settingsFragment: SettingFragment)
    fun inject(loadFragment: LoadFragment)
}