package com.lucki.hamur.di

import com.lucki.hamur.data.api.ApiFactory
import com.lucki.hamur.data.api.ApiService
import dagger.Module
import dagger.Provides

@Module
class GamesModuleProvides {

    @Provides
    fun providesApiService(): ApiService {
        return ApiFactory.create()
    }
}