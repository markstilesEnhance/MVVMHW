package com.example.w1d3_rxjavademo.view

import android.app.Application
import com.example.w1d3_rxjavademo.di.*

class TicketsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ticketComponent = DaggerTicketComponent.builder()
            .ticketsRepositoryModule(TicketsRepositoryModule())
            .viewModelFactoryModule(ViewModelFactoryModule())
            .networkModule(NetworkModule())
            .build()
    }

    companion object {
        lateinit var ticketComponent: TicketComponent
    }

}