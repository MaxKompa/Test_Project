package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.serialization.jackson.jackson
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init() // Инициализация базы данных

    install(ContentNegotiation) {
        jackson {}
    }
    install(CallLogging)
    install(StatusPages) {
        exception<Throwable> { cause, call ->
            // Преобразуем исключение в строку
            val errorMessage = cause.toString() // Получаем строковое представление исключения
            // Отправляем строку ошибки в ответ
            cause.respond(HttpStatusCode.InternalServerError, "Error occurred: $errorMessage")
        }
    }

    configureRouting()
}
