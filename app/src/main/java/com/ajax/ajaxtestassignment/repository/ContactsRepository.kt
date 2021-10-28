package com.ajax.ajaxtestassignment.repository

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.ajax.ajaxtestassignment.api.contacts.ContactsService
import com.ajax.ajaxtestassignment.api.contacts.mapper.toDbModel
import com.ajax.ajaxtestassignment.db.contacts.ContactsDao
import com.ajax.ajaxtestassignment.db.contacts.DbContact
import com.ajax.ajaxtestassignment.db.contacts.toDomainModel
import com.ajax.ajaxtestassignment.repository.model.DomainContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsRepository(val network: ContactsService, val contactsDao: ContactsDao) {
    val contacts: LiveData<List<DomainContact>> =
        contactsDao.getContacts().map { it.map { it.toDomainModel() } }

    suspend fun refreshData() {
        withContext(Dispatchers.Default) {
            try {
                val result = network.getContacts()
                result.results?.map { it.toDbModel() }?.let { contactsDao.addAll(it) }
            } catch (error: Throwable) {
                throw NetworkErrorException("Unable to refresh contacts", error)
            }
        }
    }

    suspend fun delete(id: Int){
        contactsDao.deleteById(id)
    }

    suspend fun deleteAll(){
        contactsDao.deleteAll()
    }

    suspend fun update(contact: DbContact){
        contactsDao.update(contact)
    }

    val requestedContact: MutableLiveData<DomainContact> = MutableLiveData()

    suspend fun requestContact(id: Int){
        withContext(Dispatchers.Default) {
            val contact = contactsDao.getContact(id)
            requestedContact.postValue(contact.toDomainModel())
        }
    }
}
