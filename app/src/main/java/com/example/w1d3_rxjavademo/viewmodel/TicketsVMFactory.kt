package com.example.w1d3_rxjavademo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.w1d3_rxjavademo.network.TicketsRepository

class TicketsVMFactory(val repo: TicketsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TicketsViewModel(repo) as T
    }
}