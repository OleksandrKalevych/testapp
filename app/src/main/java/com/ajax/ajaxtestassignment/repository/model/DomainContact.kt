package com.ajax.ajaxtestassignment.repository.model

import com.ajax.ajaxtestassignment.ui.model.ContactPresentation

data class DomainContact(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val photo: String?
){
    fun toPresentationModel(): ContactPresentation{
        return ContactPresentation(id, firstName, lastName, email, photo)
    }
}


