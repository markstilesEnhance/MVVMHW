package com.example.w1d3_rxjavademo.di

import com.example.w1d3_rxjavademo.network.TicketsRepository
import com.example.w1d3_rxjavademo.viewmodel.TicketsVMFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideFactory(repository: TicketsRepository): TicketsVMFactory{
        return TicketsVMFactory(repository)
    }
}