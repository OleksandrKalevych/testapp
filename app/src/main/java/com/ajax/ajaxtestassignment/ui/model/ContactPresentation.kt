package com.ajax.ajaxtestassignment.ui.model

import com.ajax.ajaxtestassignment.db.contacts.DbContact

data class ContactPresentation(
    val id: Int = 0,
    val firstName: String = "",
    val lastName: String ="",
    val email: String="",
    val photo: String? = null
) {
    fun toDbContact(): DbContact {
        return DbContact(id, firstName, lastName, email, photo)
    }
}