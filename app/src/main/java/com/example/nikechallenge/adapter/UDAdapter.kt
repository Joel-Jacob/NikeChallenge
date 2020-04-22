package com.example.nikechallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikechallenge.R
import com.example.nikechallenge.model.X

class UDAdapter: RecyclerView.Adapter<UDAdapter.DefinitionViewHolder>() {
    var definitionList = ArrayList<X>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.definition_layout, parent, false)
        return DefinitionViewHolder(view)
    }

    override fun getItemCount(): Int = definitionList.size

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.definitionTextView.text = definitionList[position].definition
        holder.thumbsUpTextView.text = definitionList[position].thumbs_up.toString()
        holder.thumbsDownTextView.text = definitionList[position].thumbs_down.toString()
    }

    class DefinitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var definitionTextView = itemView.findViewById<TextView>(R.id.definitionTextView)
        var thumbsUpTextView = itemView.findViewById<TextView>(R.id.thumbsUpTextView)
        var thumbsDownTextView = itemView.findViewById<TextView>(R.id.thumbsDownTextView)
    }
}