package com.example.w1d3_rxjavademo.di

import com.example.w1d3_rxjavademo.network.ApiService
import com.example.w1d3_rxjavademo.network.TicketsRepository
import dagger.Module
import dagger.Provides

@Module
class TicketsRepositoryModule {

    @Provides
    fun provideRepositoryModule(ticketService: ApiService): TicketsRepository {
        return TicketsRepository(ticketService)
    }
}