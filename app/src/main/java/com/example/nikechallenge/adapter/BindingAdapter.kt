package com.example.nikechallenge.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("uDAdapter")
fun setAdapter(view: RecyclerView, adapter: UDAdapter){
    view.adapter = adapter
}