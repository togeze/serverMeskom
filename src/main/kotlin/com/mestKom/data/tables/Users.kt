package com.mestKom.data.tables

import org.jetbrains.exposed.sql.Table

object Users : Table(){
    val username = varchar("username", 12)
    val password = varchar("password ", 100)
    val email = varchar("email", 30)
    val salt = varchar("salt", 100)
    val id = integer("id")

    override val primaryKey: PrimaryKey = PrimaryKey(id)

}