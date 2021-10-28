package com.ajax.ajaxtestassignment.repository

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import com.ajax.ajaxtestassignment.api.contacts.ContactsService
import com.ajax.ajaxtestassignment.api.contacts.mapper.toDbModel
import com.ajax.ajaxtestassignment.db.contacts.ContactsDao
import com.ajax.ajaxtestassignment.db.contacts.DbContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsRepository(val network: ContactsService, val contactsDao: ContactsDao) {
    val contacts: LiveData<List<DbContact>> = contactsDao.getContacts()

    suspend fun refreshData() {
        withContext(Dispatchers.Default) {
            try {
                val result = network.getContacts(limit = 10)
                result.results?.map { it.toDbModel() }?.let { contactsDao.addAll(it) }
            } catch (error: Throwable) {
                throw NetworkErrorException("Unable to refresh contacts", error)
            }
        }
    }
}
