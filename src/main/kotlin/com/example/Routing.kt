package com.example

import java.io.File
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.http.content.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {
        route("/profile") {
            // Получение профиля
            get {
                val profile = transaction {
                    user_profiles.selectAll().map {
                        Profile(
                            id = it[user_profiles.id],
                            name = it[user_profiles.name],
                            surname = it[user_profiles.surname],
                            jobTitle = it[user_profiles.jobTitle],
                            phone = it[user_profiles.phone],
                            address = it[user_profiles.address],
                            interests = it[user_profiles.interests]?.split(","),
                            isPublic = it[user_profiles.isPublic],
                            avatarUrl = it[user_profiles.avatarUrl]
                        )
                    }.firstOrNull()
                }
                if (profile != null) {
                    call.respond(profile)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Profile not found")
                }
            }

            // Обновление профиля
            put {
                val profile = call.receive<Profile>()
                transaction {
                    user_profiles.update {
                        it[name] = profile.name
                        it[surname] = profile.surname
                        it[jobTitle] = profile.jobTitle
                        it[phone] = profile.phone
                        it[address] = profile.address
                        it[interests] = profile.interests?.joinToString(",")
                        it[isPublic] = profile.isPublic
                        it[avatarUrl] = profile.avatarUrl
                    }
                }
                call.respond(HttpStatusCode.OK, profile)
            }
        }

        // Загрузка аватара
        post("/upload-avatar") {
            val multipart = call.receiveMultipart()
            var fileName: String? = null

            multipart.forEachPart { part ->
                if (part is PartData.FileItem) {
                    fileName = "avatars/${System.currentTimeMillis()}_${part.originalFileName}"
                    part.streamProvider().use { inputStream ->
                        File(fileName!!).outputStream().buffered().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                }
                part.dispose()
            }

            if (fileName != null) {
                val url = "http://localhost:8080/$fileName"
                call.respond(mapOf("avatarUrl" to url))
            } else {
                call.respond(HttpStatusCode.BadRequest, "No file uploaded")
            }
        }
    }
}
