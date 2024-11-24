/*package com.example

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Profile> { profile ->
            val errors = mutableListOf<String>()

            if (profile.name.isBlank() || profile.name.length !in 2..50)
                errors.add("Invalid name length")
            if (profile.surname.isBlank() || profile.surname.length !in 2..50)
                errors.add("Invalid surname length")
            if (profile.phone.isBlank() || !profile.phone.matches(Regex("\\+\\d{10,15}")))
                errors.add("Invalid phone number format")
            if (profile.address != null && profile.address.length > 200)
                errors.add("Address too long")
            if (profile.interests != null && profile.interests.any { it.length > 30 })
                errors.add("One or more interests are too long")
            if (profile.avatarUrl != null && !profile.avatarUrl.matches(Regex("http(s)?://.*")))
                errors.add("Invalid avatar URL")

            if (errors.isNotEmpty())
                ValidationResult.Invalid(errors.joinToString(", "))
            else
                ValidationResult.Valid
        }
    }
}
*/