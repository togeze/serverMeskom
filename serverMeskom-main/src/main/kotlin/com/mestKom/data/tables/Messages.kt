package com.mestKom.data.tables

import com.mestKom.data.tables.Users.defaultExpression
import com.mestKom.data.tables.Users.username
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object Messages : IntIdTable(){
    val time = datetime("time").defaultExpression(CurrentDateTime)
    val content = text("content")
    val from_id = reference("from_id", username)
    val to_id = reference("to_id", username)
}