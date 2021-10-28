package com.ajax.ajaxtestassignment.ui.contactslist.adapter

import com.ajax.ajaxtestassignment.ui.model.ContactPresentation

interface OnContactItemClickListener {
    fun onItemClick(item: ContactPresentation)
}