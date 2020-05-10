package com.example.w1d3_rxjavademo.network

import com.example.w1d3_rxjavademo.network.model.Price
import com.example.w1d3_rxjavademo.network.model.Ticket
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TicketsRepository @Inject constructor(private val ticketService: ApiService) {

    fun getTickets(from: String, to: String): Single<MutableList<Ticket>> {
        return getTicketsFromRemote(from, to)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getTicketsFromRemote(from: String, to: String): Single<MutableList<Ticket>> {
        return ticketService.searchTickets(from, to) as Single<MutableList<Ticket>>
    }

    fun getPrices(flightNum: String, from: String, to: String): Single<Price> {
        return getPricesFromRemote(flightNum, from, to)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getPricesFromRemote(flightNum: String, from: String, to: String): Single<Price> {
        return ticketService.getPrice(flightNum, from, to)
    }


}