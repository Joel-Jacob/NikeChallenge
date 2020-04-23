package com.example.nikechallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikechallenge.R
import com.example.nikechallenge.model.Definition
import kotlinx.android.synthetic.main.definition_layout.view.*

class UDAdapter: RecyclerView.Adapter<UDAdapter.DefinitionViewHolder>() {
    var definitionList = ArrayList<Definition>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.definition_layout, parent, false)
        return DefinitionViewHolder(view)
    }

    override fun getItemCount(): Int = definitionList.size

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.itemView.definitionTextView.text = definitionList[position].definition
        holder.itemView.thumbsUpTextView.text = definitionList[position].thumbs_up.toString()
        holder.itemView.thumbsDownTextView.text = definitionList[position].thumbs_down.toString()
    }

    class DefinitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}