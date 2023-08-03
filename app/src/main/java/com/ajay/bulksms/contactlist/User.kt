package com.ajay.bulksms.contactlist

data class User(
    val id: Int,
    val initials: String,
    val name: String,
    val mobileNumber: String,
)

data class Contact(
    val id: String,
    val initials: String,
    val displayName: String,
    val phoneNumber: String,
    val isSelected: Boolean
)
