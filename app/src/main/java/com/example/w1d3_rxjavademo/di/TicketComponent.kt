package com.example.w1d3_rxjavademo.di

import com.example.w1d3_rxjavademo.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TicketsRepositoryModule::class,
    NetworkModule::class,
    ViewModelFactoryModule::class
])
interface TicketComponent {
    fun inject(mainActivity: MainActivity)
}