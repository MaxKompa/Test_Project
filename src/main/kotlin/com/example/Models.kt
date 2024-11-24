package com.example

data class Profile(
    val id: Int? = null,
    val name: String,
    val surname: String,
    val jobTitle: String?,
    val phone: String,
    val address: String?,
    val interests: List<String>?,
    val isPublic: Boolean,
    val avatarUrl: String?
)
