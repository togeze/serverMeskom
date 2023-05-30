package com.mestKom.data.tables

import com.mestKom.data.tables.Videos.autoIncrement
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNull

import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object Users : IntIdTable(){
    val username = varchar("username", 12)
    val password = varchar("password ", 100)
    val email = varchar("email", 30)
    val salt = varchar("salt", 100)
    val status = varchar("status", 64)
    val enabled = integer("enable")
    val image = varchar("image", 255)
    val dataCreated = datetime("date_created").defaultExpression(CurrentDateTime)
    val role_id = reference("role_id", Roles)
    
}