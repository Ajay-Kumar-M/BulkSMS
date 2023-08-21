package com.ajay.bulksms.contactlist

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    val id: Int,
    val initials: String,
    val name: String,
    val mobileNumber: String,
)

@Entity(tableName = "Contacts")
data class Contacts(
    @PrimaryKey//(autoGenerate = true)
    val id: Int,
    val initials: String,
    val displayName: String,
    val phoneNumber: String
)