package com.example.w1d3_rxjavademo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.w1d3_rxjavademo.network.TicketsRepository
import com.example.w1d3_rxjavademo.network.model.Price
import com.example.w1d3_rxjavademo.network.model.Ticket
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TicketsViewModel @Inject constructor(private val repository: TicketsRepository): ViewModel() {

    private val disposable = CompositeDisposable()
    private val ticketLiveData = MutableLiveData<TicketState>()
    private val priceLiveData = MutableLiveData<PriceState>()

    sealed class PriceState {
        object LOADING: PriceState()
        data class PRICE_SUCCESS(val price: Price): PriceState()
        data class ERROR(val errorMessage: String): PriceState()
    }

    fun getPriceState(): LiveData<PriceState> {
        return priceLiveData
    }

    sealed class TicketState {
        object LOADING: TicketState()
        data class TICKET_SUCCESS(val list: MutableList<Ticket>): TicketState()
        data class ERROR(val errorMessage: String): TicketState()
    }

    fun getTicketState(): LiveData<TicketState> {
        return ticketLiveData
    }

    fun getTickets(from: String, to: String) {
        ticketLiveData.value = TicketState.LOADING
        disposable.add(
            repository
                .getTickets(from, to)
                .subscribe({
                    ticketLiveData.value = TicketState.TICKET_SUCCESS(it)
                },{
                    ticketLiveData.value = TicketState.ERROR(it.message!!)
                })
        )
    }

    fun getPrices(flightNumber: String, from: String, to: String) {
        priceLiveData.value = PriceState.LOADING
        disposable.add(
            repository
                .getPrices(flightNumber, from, to)
                .subscribe({
                    priceLiveData.value = PriceState.PRICE_SUCCESS(it)
                },{
                    priceLiveData.value = PriceState.ERROR(it.message!!)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}