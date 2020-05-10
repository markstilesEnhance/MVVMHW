package com.example.w1d3_rxjavademo.view

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w1d3_rxjavademo.R
import com.example.w1d3_rxjavademo.network.model.Ticket
import kotlin.math.roundToInt
import com.example.w1d3_rxjavademo.network.TicketsRepository
import com.example.w1d3_rxjavademo.viewmodel.TicketsVMFactory
import com.example.w1d3_rxjavademo.viewmodel.TicketsViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val from = "DEL"
    private val to = "HYD"
    lateinit var mAdapter: TicketsAdapter
    private var ticketsList: MutableList<Ticket> = mutableListOf()
    lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var viewModelFactory: TicketsVMFactory
    private lateinit var viewModel: TicketsViewModel
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        TicketsApplication.ticketComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "$from > $to"
        initRecyclerView()

        viewModel = viewModelFactory.create(TicketsViewModel::class.java)
        viewModel.getTicketState().observe(this, Observer { ticketState ->
            when(ticketState) {
                is TicketsViewModel.TicketState.LOADING -> {}
                is TicketsViewModel.TicketState.TICKET_SUCCESS -> {
                    ticketsList = ticketState.list
                    mAdapter.updateTickets(ticketsList)
                }
//                is TicketsViewModel.AppState.PRICE_SUCCESS -> {
//                    ticketsList[position].price = ticketState.price
//                    mAdapter.updatePrice(ticketsList[position], position)
//                }
            }
        })
        viewModel.getPriceState().observe(this, Observer { priceState ->
            when(priceState) {
                is TicketsViewModel.PriceState.LOADING -> {}
                is TicketsViewModel.PriceState.PRICE_SUCCESS -> {
                    ticketsList[position].price = priceState.price
                    mAdapter.updatePrice(ticketsList[position], position)
                }
            }
        })

        viewModel.getTickets(from, to)

        for(index in ticketsList.indices) {
            position = index
            viewModel.getPrices(ticketsList[index].flightNumber, from, to)
        }

    }

    private fun initRecyclerView() {
        mAdapter = TicketsAdapter(applicationContext, ticketsList){ ticket : Ticket -> onTicketSelected(ticket) }
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(GridSpacingItemDecoration(1, dpToPx(5), true))
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter
    }

    private fun dpToPx(dp: Int): Int {
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            r.displayMetrics
        ).roundToInt()
    }

    private fun onTicketSelected(ticket: Ticket?) {
        Toast.makeText(this, "Clicked: ${ticket?.flightNumber}", Toast.LENGTH_LONG).show()
    }

}