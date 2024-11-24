package com.example

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

object DatabaseFactory {
    fun init() {
        Database.connect("jdbc:sqlite:D:/Maks/SQLite/profile.db", driver = "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            create(user_profiles) // Создаем таблицу
        }
    }
}

object user_profiles : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val surname = varchar("surname", 50)
    val jobTitle = varchar("job_title", 100).nullable()
    val phone = varchar("phone", 15)
    val address = varchar("address", 200).nullable()
    val interests = varchar("interests", 255).nullable() // Список интересов в виде строки через запятую
    val isPublic = bool("is_public").default(false)
    val avatarUrl = varchar("avatar_url", 200).nullable()

    override val primaryKey = PrimaryKey(id)
}
