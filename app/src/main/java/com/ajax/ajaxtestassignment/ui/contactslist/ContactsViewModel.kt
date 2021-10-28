package com.ajax.ajaxtestassignment.ui.contactslist

import android.accounts.NetworkErrorException
import androidx.lifecycle.*
import com.ajax.ajaxtestassignment.repository.ContactsRepository
import kotlinx.coroutines.launch

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {

    val contacts = repository.contacts.map { it.map { it.toPresentationModel() } }

    fun onReload() {
        launchDataLoad {
            repository.refreshData()
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Unit {
        viewModelScope.launch {
            try {
                // we can run loading here
                block()
            } catch (error: NetworkErrorException) {
                //setup error message idk i have no time
            } finally {
                // disable loading
            }
        }
    }

    companion object { val FACTORY = singleArgViewModelFactory(::ContactsViewModel) }
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
