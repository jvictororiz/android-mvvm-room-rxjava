package com.example.myapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.databinding.ItemCharacterBinding
import com.example.myapp.domain.Character

class RickAndMortyAdapter(
    private val characters: List<Character>,
    private val onItemClick: ((Character) -> Unit)
) : RecyclerView.Adapter<RickAndMortyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding?.character = characters[position]
        binding?.executePendingBindings()
    }

    override fun getItemCount(): Int = characters.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemCharacterBinding? = ItemCharacterBinding.bind(view)

        init {
            view.setOnClickListener {
                onItemClick.invoke(characters[adapterPosition])
            }
        }
    }

}