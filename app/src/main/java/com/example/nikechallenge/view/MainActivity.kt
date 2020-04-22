package com.example.nikechallenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nikechallenge.R
import com.example.nikechallenge.databinding.ActivityMainBinding
import com.example.nikechallenge.viewmodel.UDViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: UDViewModel
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(UDViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.udViewmodel = viewModel

        viewModel.getDefinitions("wat")

    }
}
