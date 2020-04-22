package com.example.nikechallenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nikechallenge.R
import com.example.nikechallenge.databinding.ActivityMainBinding
import com.example.nikechallenge.viewmodel.UDViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: UDViewModel
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(UDViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.udViewmodel = viewModel

        setUp()
    }

    private fun setUp() {
        searchButton.setOnClickListener {
            loadingSpinner.visibility = View.VISIBLE
            viewModel.getDefinitions(searchInput.text.toString())
            recyclerView.smoothScrollToPosition(0)
        }

        thumbsButton.setOnClickListener {
            viewModel.sortList()
            recyclerView.smoothScrollToPosition(0)
        }

        val spinnerObserver = Observer<Boolean> {
            loadingSpinner.visibility = View.GONE
        }

        viewModel.spinner.observe(this, spinnerObserver)
    }
}
