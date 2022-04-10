package com.example.gamewebview.di

import com.example.gamewebview.data.api.ApiFactory
import com.example.gamewebview.data.api.ApiService
import dagger.Module
import dagger.Provides

@Module
class GamesModuleProvides {

    @Provides
    fun providesApiService(): ApiService {
        return ApiFactory.create()
    }
}