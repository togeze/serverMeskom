package com.mestKom.database

import com.mestKom.data.tables.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val database = Database.connect(
            url = "jdbc:postgresql://localhost:5432/mestKom",
            driver = "org.postgresql.Driver",
            user = "test",
            password = "test"
        )

        transaction(database) {
            SchemaUtils.create(Users)
        }
    }



    suspend fun <T> dbQuery(block: suspend() -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}