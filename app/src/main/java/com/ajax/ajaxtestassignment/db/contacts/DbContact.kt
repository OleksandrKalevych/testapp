package com.ajax.ajaxtestassignment.db.contacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ajax.ajaxtestassignment.repository.model.DomainContact

@Entity(tableName = "Contact")
data class DbContact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val firstName: String?,
    @ColumnInfo val lastName: String?,
    @ColumnInfo val email: String?,
    @ColumnInfo val photo: String?,
)

fun DbContact.toDomainModel(): DomainContact {
    return DomainContact(id, firstName?:"", lastName?: "", email?:"", photo)
}