package com.ajax.ajaxtestassignment.ui.contactslist

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajax.ajaxtestassignment.databinding.ItemContactListBinding

class ContactAdapter (var items: List<String>, private val context: Activity) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            ItemContactListBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = items[position];
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder (binding: ItemContactListBinding) : RecyclerView.ViewHolder(binding.root) {
    val text = binding.text
}