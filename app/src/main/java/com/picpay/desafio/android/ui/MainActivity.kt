package com.picpay.desafio.android.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.ui.MainActivityViewState.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.user_list_progress_bar) }

    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUsersList()
        observeViewState()

        viewModel.onViewReady()
    }

    private fun setupUsersList() {
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { state ->
            when (state) {
                is Loading -> {
                    handleLoading(showLoading = true)
                }
                is Error -> {
                    handleLoading(showLoading = false)
                    Toast.makeText(this, "${state.cause.message}", Toast.LENGTH_SHORT).show()
                }
                is ShowUsers -> {
                    handleLoading(showLoading = false)
                    adapter.users = state.users
                }
            }
        })
    }

    private fun handleLoading(showLoading: Boolean) {
        progressBar.visibility = if (showLoading) View.VISIBLE else View.GONE
    }
}
