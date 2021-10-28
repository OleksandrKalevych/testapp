package com.ajax.ajaxtestassignment.ui.contactslist

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajax.ajaxtestassignment.databinding.ItemContactListBinding
import com.ajax.ajaxtestassignment.ui.model.ContactPresentation
import com.bumptech.glide.Glide

import com.ajax.ajaxtestassignment.ui.contactslist.adapter.OnContactItemClickListener

class ContactAdapter (var items: List<ContactPresentation>, private val context: Activity, val clickListener: OnContactItemClickListener) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view =  ItemContactListBinding.inflate(LayoutInflater.from(context), parent, false)

        view.allArea.setOnClickListener {
            clickListener.onItemClick(items[position])
        }

        return ViewHolder(view)
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