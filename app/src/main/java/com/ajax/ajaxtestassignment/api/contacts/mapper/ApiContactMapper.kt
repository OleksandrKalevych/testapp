package com.ajax.ajaxtestassignment.api.contacts.mapper

import com.ajax.ajaxtestassignment.api.contacts.ApiContact
import com.ajax.ajaxtestassignment.db.contacts.DbContact

fun ApiContact.toDbModel(): DbContact {
    return DbContact(firstName = name?.firstName, lastName = name?.lastName, email = email, photo = picture?.medium)
}