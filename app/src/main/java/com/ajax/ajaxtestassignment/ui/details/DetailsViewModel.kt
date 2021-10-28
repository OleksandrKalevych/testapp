package com.ajax.ajaxtestassignment.ui.details

import androidx.lifecycle.*
import com.ajax.ajaxtestassignment.db.contacts.DbContact
import com.ajax.ajaxtestassignment.repository.ContactsRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: ContactsRepository) : ViewModel() {

    val contact = repository.requestedContact.map {  it.toPresentationModel() }

    fun requestContact(id: Int) {
        viewModelScope.launch {
            repository.requestContact(id)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    fun update(contact: DbContact){
        viewModelScope.launch {
            repository.update(contact)
        }
    }

    companion object { val FACTORY = singleArgViewModelFactory(::DetailsViewModel) }
}

fun <T : ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T):
        (A) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}
