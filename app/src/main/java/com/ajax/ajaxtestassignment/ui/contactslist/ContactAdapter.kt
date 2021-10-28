package com.ajax.ajaxtestassignment.ui.contactslist

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajax.ajaxtestassignment.databinding.ItemContactListBinding
import com.ajax.ajaxtestassignment.ui.model.ContactPresentation
import com.bumptech.glide.Glide

class ContactAdapter (var items: List<ContactPresentation>, private val context: Activity) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            ItemContactListBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mail.text = items[position].email
        holder.firstName.text = items[position].firstName
        holder.lastName.text = items[position].lastName

        //?
        Glide.with(holder.picture)
            .load(items[position].photo)
            .into(holder.picture)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder (binding: ItemContactListBinding) : RecyclerView.ViewHolder(binding.root) {
    val mail = binding.mail
    val firstName = binding.name
    val lastName = binding.surname
    val picture = binding.picture
}