package com.mestKom.data.tables

import com.mestKom.data.tables.Videos.autoIncrement
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Users : IntIdTable(){
    val sequelId = varchar("sequel_id", 100).uniqueIndex()
    val username = varchar("username", 12)
    val password = varchar("password ", 100)
    val email = varchar("email", 30)
    val salt = varchar("salt", 100)
}